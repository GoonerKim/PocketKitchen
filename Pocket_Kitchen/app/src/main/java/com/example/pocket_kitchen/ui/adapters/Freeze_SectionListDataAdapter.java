package com.example.pocket_kitchen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Cold_SingleItemModel;
import com.example.pocket_kitchen.datas.Freeze_SingleItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Freeze_SectionListDataAdapter extends RecyclerView.Adapter<Freeze_SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Freeze_SingleItemModel> freeze_itemsList;
    private Context mContext;

    public Freeze_SectionListDataAdapter(Context context, ArrayList<Freeze_SingleItemModel> freeze_itemsList) {
        this.freeze_itemsList = freeze_itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freeze_list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Freeze_SingleItemModel singleItem = freeze_itemsList.get(i);

        holder.freeze_title.setText(singleItem.getFreeze_title());
        holder.freeze_D_day.setText(singleItem.getFreeze_D_day());

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != freeze_itemsList ? freeze_itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView freeze_title;

        protected TextView freeze_D_day;

        protected FloatingActionButton freeze__fab;


        public SingleItemRowHolder(View view) {
            super(view);

            this.freeze_title = (TextView) view.findViewById(R.id.freeze_title);
            this.freeze_D_day = (TextView) view.findViewById(R.id.freeze_D_day);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), freeze_title.getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
