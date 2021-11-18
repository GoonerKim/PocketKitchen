package com.example.pocket_kitchen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Cold;
import com.example.pocket_kitchen.datas.Freeze;

import java.util.ArrayList;

public class Freeze_RecyclerViewAdapter extends RecyclerView.Adapter<Freeze_RecyclerViewAdapter.ItemRowHolder> {

    private ArrayList<Freeze> freezes = new ArrayList<>();
    private Context freeze_context;

    public Freeze_RecyclerViewAdapter(Context freeze_context) {
        this.freeze_context = freeze_context;
    }

    @Override
    public int getItemCount() {
        return freezes.size();
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freeze_list_single_card, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView freeze_title, freeze_D_day;

        public ItemRowHolder(View view) {
            super(view);
            this.freeze_title = view.findViewById(R.id.freeze_title);
            this.freeze_D_day = view.findViewById(R.id.freeze_D_day);
        }
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int i) {
        Freeze data = freezes.get(i);

        holder.freeze_title.setText(data.getTitle());
        holder.freeze_D_day.setText(data.getExpiration());
    }

    public void add(Freeze data) { freezes.add(data);
    }
}