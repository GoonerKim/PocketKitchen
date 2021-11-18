package com.example.pocket_kitchen.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Cold;
import com.example.pocket_kitchen.datas.Freeze;
import com.example.pocket_kitchen.ui.activities.Cold_Add_Activity;
import com.example.pocket_kitchen.ui.activities.Freeze_Add_Activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class FreezeRecyclerAdapter extends RecyclerView.Adapter<FreezeRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Freeze> freezes = new ArrayList<>(); //arraylist 자체가 List값만 받는 리스트임
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public FreezeRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FreezeRecyclerAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freeze_list_single_card, null));
    }

    @Override
    public int getItemCount() {
        return freezes.size();
    }

    public void add(Freeze list) { //lists array에 list 데이터 추가
        freezes.add(list);
    }

    public void clear() { //lists array를 모두 삭제시킴
        freezes.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(freezes.get(i));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void bind(final Freeze freeze) { //list와 관리자 여부를 bind함
            TextView freezeTitle = view.findViewById(R.id.freeze_title);
            TextView freezeDday = view.findViewById(R.id.freeze_D_day);
            LinearLayout layoutBg = view.findViewById(R.id.layout_bg);

            freezeTitle.setText(freeze.getTitle());

            layoutBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialogView = inflater.inflate(R.layout.dialog_freeze, null);

                    TextView textTitle, textCount, textLayer, textExpiration, textMemo, textAlarm;
                    textTitle = dialogView.findViewById(R.id.freeze_text_title);
                    textTitle.setText(freeze.getTitle());
                    textCount = dialogView.findViewById(R.id.freeze_text_count);
                    textCount.setText(String.format("개수 : %s", freeze.getCount()));
                    textLayer = dialogView.findViewById(R.id.freeze_text_layer);
                    textLayer.setText(String.format("층수 : %s", freeze.getLayer()));
                    textExpiration = dialogView.findViewById(R.id.freeze_text_expiration);
                    textExpiration.setText(String.format("유통기한 : %s", freeze.getExpiration()));
                    textAlarm = dialogView.findViewById(R.id.freeze_text_alarm);
                    textAlarm.setText(String.format("알람 : %s", freeze.getAlarmTime()));
                    textMemo = dialogView.findViewById(R.id.freeze_text_memo);
                    textMemo.setText(freeze.getMemo());

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setView(dialogView);
                    dialog.setPositiveButton("닫기", (dialog12, which) -> {
                    });
                    dialog.show();
                }
            });

            layoutBg.setOnLongClickListener(v -> {
                final CharSequence[] select = {"편집", "삭제"}; //본인이 작성 -> 편집 가능

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(freeze.getTitle());
                builder.setItems(select, (dialog, pos) -> {
                    if (select[pos].equals("편집")) {
                        Intent intent = new Intent(context, Freeze_Add_Activity.class);
                        intent.putExtra("edit", freeze.getRegisteredDate().toString()); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                        intent.putExtra("layer", freeze.getLayer());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    if (select[pos].equals("삭제")) {
                        AlertDialog.Builder delete = new AlertDialog.Builder(context);
                        delete.setTitle("삭제");
                        delete.setMessage("정말 삭제하시겠습니까?");
                        delete.setPositiveButton("예", (dialog1, which) -> {
                            SharedPreferences sp = context.getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("alarm" + freeze.getService(), false);
                            editor.apply();

                            firestore.collection("users")
                                    .document(Objects.requireNonNull(sp.getString("uid", "0")))
                                    .collection("freeze" + freeze.getLayer())
                                    .document(freeze.getRegisteredDate().toString()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            freezes.remove(getAdapterPosition());
                                            notifyItemRemoved(getAdapterPosition());
                                        }
                                    });
                        });
                        delete.setNegativeButton("아니오", (dialog12, which) -> {
                        });
                        delete.show();
                    }
                });
                builder.show();
                return true;
            });

            if (!freeze.getExpiration().equals("설정되지 않음.")) { //유통기한이 설정되어 있는 경우
                /** 유통기한의 년/월/일을 나누어 날짜 계산함 */
                int year = Integer.parseInt(freeze.getExpiration().substring(0, 4));
                int month = Integer.parseInt(freeze.getExpiration().substring(5, 7));
                int day = Integer.parseInt(freeze.getExpiration().substring(8, 10));

                int dday = countdday(year, month, day);

                /** DDay 설정을 해주고, 날짜와 관련하여 색상을 부여함. 기본 색상을 FFFFFF으로 함
                 * recyclerview는 재사용되기 때문에 기본 색상을 정해주지 않으면 색상이 정상적으로 출력되지 않음*/
                freezeDday.setTextColor(Color.parseColor("#ffffff"));
                if (dday > 0) {
                    freezeDday.setText(String.format("D-%s", dday));
                    if (dday <= 3) freezeDday.setTextColor(Color.parseColor("#ff0000"));
                } else if (dday < 0) {
                    freezeDday.setText(String.format("D+%s", String.valueOf(dday).substring(1)));
                    freezeDday.setTextColor(Color.parseColor("#ffa800"));
                } else {
                    freezeDday.setText("D-Day");
                    freezeDday.setTextColor(Color.parseColor("#ff0000"));
                }
            } else {
                /** 가져온 날짜를 기준으로 년/월/일을 나누고, 날짜 계산을 함.
                 * 오늘 가져왔다면 오늘 등록이라는 글자를 출력하고, 그 외에는 D+00 이런식으로 표시함 */
                int year = Integer.parseInt(freeze.getExpirationStart().substring(0, 4));
                int month = Integer.parseInt(freeze.getExpirationStart().substring(5, 7));
                int day = Integer.parseInt(freeze.getExpirationStart().substring(8, 10));

                int dday = countdday(year, month, day);
                if (dday == 0) {
                    freezeDday.setText("오늘 등록");
                } else {
                    freezeDday.setText(String.format("D+%s", String.valueOf(dday).substring(1)));
                    freezeDday.setTextColor(Color.parseColor("#ffa800"));
                }
            }
        }

        int countdday(int myear, int mmonth, int mday) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
                Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

                mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
                ddayCal.set(myear, mmonth, mday);// D-day의 날짜를 입력

                long today = todaCal.getTimeInMillis() / 86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
                long dday = ddayCal.getTimeInMillis() / 86400000;
                long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
                return (int) count;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
}