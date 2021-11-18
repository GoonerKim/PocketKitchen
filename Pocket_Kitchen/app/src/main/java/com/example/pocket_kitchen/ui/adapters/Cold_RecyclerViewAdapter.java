package com.example.pocket_kitchen.ui.adapters;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;

        import com.example.pocket_kitchen.R;
        import com.example.pocket_kitchen.datas.Cold;

        import java.util.ArrayList;

public class Cold_RecyclerViewAdapter extends RecyclerView.Adapter<Cold_RecyclerViewAdapter.ItemRowHolder> {

    private ArrayList<Cold> colds = new ArrayList<>();
    private Context context;

    public Cold_RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return colds.size();
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cold_list_single_card, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView cold_title, cold_D_day;

        public ItemRowHolder(View view) {
            super(view);
            this.cold_title = view.findViewById(R.id.cold_title);
            this.cold_D_day = view.findViewById(R.id.cold_D_day);
        }
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int i) {
        Cold data = colds.get(i);

        holder.cold_title.setText(data.getTitle());
        holder.cold_D_day.setText(data.getExpiration());
    }

    public void add(Cold data) {
        colds.add(data);
    }
}