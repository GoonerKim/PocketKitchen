package com.example.pocket_kitchen.ui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Notice;
import com.example.pocket_kitchen.ui.activities.Honey_tip_add_notice;
import com.example.pocket_kitchen.ui.activities.My_Recipe_add_notice;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HoneyRecyclerAdapter extends RecyclerView.Adapter<HoneyRecyclerAdapter.ViewHolder> {

    /**
     * lists 라는 큰 틀이 있고, 그 틀에는 list array만 넣을 수 있음.
     * 2차원 배열이라고 생각하면 쉬움.
     * <p>
     * |----------------|    insert, edit, remove   |------------|
     * |     lists      |  <----------------------  |  each list |
     * |----------------|                           |------------|
     **/

    private Context context;
    private String uid;
    private ArrayList<Notice> lists = new ArrayList<>(); //arraylist 자체가 List값만 받는 리스트임
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public HoneyRecyclerAdapter(Context context, String uid) {
        this.context = context;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HoneyRecyclerAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview, null));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void add(Notice list) { //lists array에 list 데이터 추가
        lists.add(list);
    }

    public void replace(Notice list) { //lists array에서 변경하려고 하는 list 값과 일치한 id를 찾아 변경
        int previousPosition = -1;
        for (Notice eachList : lists) {
            if (eachList.getListId().equals(list.getListId())) {
                previousPosition = lists.indexOf(eachList);
                break;
            }
        }

        if (previousPosition == -1) return;
        lists.set(previousPosition, list);
    }

    public void remove(Notice list) { //lists array에서 삭제하려고 하는 list 값과 일치한 id를 찾아 삭제
        int previousPosition = -1;
        for (Notice eachList : lists) {
            if (eachList.getListId().equals(list.getListId())) {
                previousPosition = lists.indexOf(eachList);
                break;
            }
        }

        if (previousPosition == -1) return;
        lists.remove(previousPosition);
    }

    public void clear() { //lists array를 모두 삭제시킴
        lists.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(lists.get(i));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        @SuppressLint("SetTextI18n")
        void bind(final Notice list) { //list와 관리자 여부를 bind함
            TextView editor = view.findViewById(R.id.editor);
            TextView title = view.findViewById(R.id.title);
            TextView content = view.findViewById(R.id.content);

            editor.setText(list.getTime() + "\n" + list.getName());
            title.setText(list.getTitle());
            content.setText(list.getContent());

            title.setTextSize(list.getTitleSize());
            content.setTextSize(list.getTextSize());

            switch (list.getTitleColor()) {
                case "Black":
                    title.setTextColor(Color.BLACK);
                    break;
                case "Red":
                    title.setTextColor(Color.RED);
                    break;
                case "Blue":
                    title.setTextColor(Color.BLUE);
                    break;
                case "Green":
                    title.setTextColor(Color.GREEN);
                    break;
            }

            switch (list.getTextColor()) {
                case "Black":
                    content.setTextColor(Color.BLACK);
                    break;
                case "Red":
                    content.setTextColor(Color.RED);
                    break;
                case "Blue":
                    content.setTextColor(Color.BLUE);
                    break;
                case "Green":
                    content.setTextColor(Color.GREEN);
                    break;
            }

            Typeface typeface;

            switch (list.getTitleFont()) {
                case "sans":
                    title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                    title.setTypeface(null, Typeface.BOLD);
                    break;
                case "serif":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                    title.setTypeface(typeface);
                    break;
                case "casual":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                    title.setTypeface(typeface);
                    break;
            }

            switch (list.getTextFont()) {
                case "sans":
                    content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                    break;
                case "serif":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                    content.setTypeface(typeface);
                    break;
                case "casual":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                    content.setTypeface(typeface);
                    break;
            }

            RelativeLayout layoutBg = view.findViewById(R.id.layout_bg);
            layoutBg.setOnLongClickListener(v -> { //Long Click 시 이벤트
                if (list.getUid().equals(uid)) { //관리자인지 확인
                    final CharSequence[] select = {"편집", "삭제"}; //본인이 작성 -> 편집 가능

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(list.getTitle());
                    builder.setItems(select, (dialog, pos) -> {
                        if (select[pos].equals("편집")) {
                            Intent intent = new Intent(context, Honey_tip_add_notice.class);
                            intent.putExtra("edit", list.getRegisteredDate().toString()); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (select[pos].equals("삭제")) {
                            AlertDialog.Builder delete = new AlertDialog.Builder(context);
                            delete.setTitle("삭제");
                            delete.setMessage("정말 삭제하시겠습니까?");
                            delete.setPositiveButton("예", (dialog1, which) ->
                                    firestore.collection("notice").document(list.getRegisteredDate().toString()).delete());
                            delete.setNegativeButton("아니오", (dialog12, which) -> {
                            });
                            delete.show();
                        }
                    });
                    builder.show();
                }
                return true;
            });

            layoutBg.setOnClickListener(v -> {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v2 = inflater.inflate(R.layout.dialog_notice, null);

                TextView tvDate, tvEditor, tvTitle, tvContent;
                tvDate = v2.findViewById(R.id.day);
                tvEditor = v2.findViewById(R.id.editor);
                tvTitle = v2.findViewById(R.id.title);
                tvContent = v2.findViewById(R.id.content);

                tvDate.setText(list.getDay());
                tvEditor.setText(list.getTime() + "\n" + list.getName());
                tvTitle.setText(list.getTitle());
                tvContent.setText(list.getContent());

                tvTitle.setTextSize(list.getTitleSize());
                tvContent.setTextSize(list.getTextSize());

                switch (list.getTitleColor()) {
                    case "Black":
                        tvTitle.setTextColor(Color.BLACK);
                        break;
                    case "Red":
                        tvTitle.setTextColor(Color.RED);
                        break;
                    case "Blue":
                        tvTitle.setTextColor(Color.BLUE);
                        break;
                    case "Green":
                        tvTitle.setTextColor(Color.GREEN);
                        break;
                }

                switch (list.getTextColor()) {
                    case "Black":
                        tvContent.setTextColor(Color.BLACK);
                        break;
                    case "Red":
                        tvContent.setTextColor(Color.RED);
                        break;
                    case "Blue":
                        tvContent.setTextColor(Color.BLUE);
                        break;
                    case "Green":
                        tvContent.setTextColor(Color.GREEN);
                        break;
                }

                Typeface typeface2;

                switch (list.getTitleFont()) {
                    case "sans":
                        tvTitle.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                        tvTitle.setTypeface(null, Typeface.BOLD);
                        break;
                    case "serif":
                        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                        tvTitle.setTypeface(typeface2);
                        break;
                    case "casual":
                        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                        tvTitle.setTypeface(typeface2);
                        break;
                }

                switch (list.getTextFont()) {
                    case "sans":
                        tvContent.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                        break;
                    case "serif":
                        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                        tvContent.setTypeface(typeface2);
                        break;
                    case "casual":
                        typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                        tvContent.setTypeface(typeface2);
                        break;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setView(v2);
                dialog.setPositiveButton("닫기", (dialog12, which) -> {
                });
                dialog.show();
            });
        }
    }
}