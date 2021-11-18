package com.example.pocket_kitchen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.datas.Cold_SingleItemModel;
import com.example.pocket_kitchen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Cold_SectionListDataAdapter extends RecyclerView.Adapter<Cold_SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Cold_SingleItemModel> itemsList;
    private Context mContext;

    public Cold_SectionListDataAdapter(Context context, ArrayList<Cold_SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cold_list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Cold_SingleItemModel singleItem = itemsList.get(i);

        holder.cold_title.setText(singleItem.getCold_title());
        holder.cold_D_day.setText(singleItem.getCold_D_day());

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView cold_title;

        protected TextView cold_D_day;

        protected FloatingActionButton cold_fab;


        public SingleItemRowHolder(View view) {
            super(view);

            this.cold_title = (TextView) view.findViewById(R.id.cold_title);
            this.cold_D_day = (TextView) view.findViewById(R.id.cold_D_day);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), cold_title.getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
