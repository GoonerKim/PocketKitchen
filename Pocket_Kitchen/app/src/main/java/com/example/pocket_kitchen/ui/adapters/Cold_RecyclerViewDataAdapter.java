package com.example.pocket_kitchen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.datas.Cold_SectionDataModel;
import com.example.pocket_kitchen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Cold_RecyclerViewDataAdapter extends RecyclerView.Adapter<Cold_RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<Cold_SectionDataModel> dataList;
    private Context mContext;

    public Cold_RecyclerViewDataAdapter(Context context, ArrayList<Cold_SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cold_list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = dataList.get(i).getHeaderTitle();
        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();
        Cold_SectionListDataAdapter itemListDataAdapter = new Cold_SectionListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected RecyclerView recycler_view_list;
        private FloatingActionButton cold_fab;

        public ItemRowHolder(View view) {
            super(view);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        }
    }
}