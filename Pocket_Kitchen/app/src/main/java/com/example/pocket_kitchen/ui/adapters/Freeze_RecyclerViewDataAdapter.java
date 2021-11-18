package com.example.pocket_kitchen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Cold_SectionDataModel;
import com.example.pocket_kitchen.datas.Freeze_SectionDataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Freeze_RecyclerViewDataAdapter extends RecyclerView.Adapter<Freeze_RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<Freeze_SectionDataModel> freeze_dataList;
    private Context mContext;

    public Freeze_RecyclerViewDataAdapter(Context context, ArrayList<Freeze_SectionDataModel> freeze_dataList) {
        this.freeze_dataList = freeze_dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freeze_list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = freeze_dataList.get(i).getFreeze_headerTitle();
        ArrayList singleSectionItems = freeze_dataList.get(i).getFreeze_allItemsInSection();
        Cold_SectionListDataAdapter itemListDataAdapter = new Cold_SectionListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.freeze_recycler_view_list.setHasFixedSize(true);
        itemRowHolder.freeze_recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.freeze_recycler_view_list.setAdapter(itemListDataAdapter);

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != freeze_dataList ? freeze_dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected RecyclerView freeze_recycler_view_list;
        private FloatingActionButton freeze_fab;

        public ItemRowHolder(View view) {
            super(view);
            this.freeze_recycler_view_list = (RecyclerView) view.findViewById(R.id.freeze_recycler_view_list);
        }
    }
}