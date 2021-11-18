package com.example.pocket_kitchen.ui.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Honey_tip_Item;
import com.example.pocket_kitchen.datas.My_Recipe_Item;
import com.example.pocket_kitchen.ui.dialogs.Honey_tip_ForDialog;
import com.example.pocket_kitchen.ui.dialogs.My_Recipe_ForDialog;

import java.util.List;


public class Honey_tip_RecyclerAdapter extends RecyclerView.Adapter<Honey_tip_RecyclerAdapter.ViewHolder> {
    Context honey_context;
    List<Honey_tip_Item> honey_items;
    int honey_item_layout;
    Activity honey_activity;


    public Honey_tip_RecyclerAdapter(Context context, List<Honey_tip_Item> honey_items, int honey_item_layout) {
        this.honey_context = context;
        this.honey_items = honey_items;
        this.honey_item_layout = honey_item_layout;
    }

    public Honey_tip_RecyclerAdapter(List<Honey_tip_Item> honey_items) {
        this.honey_items = honey_items;
        this.honey_activity = honey_activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.honey_item_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Honey_tip_Item item = honey_items.get(position);

        holder.honey_cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


//                Toast.makeText(context, "long Click", Toast.LENGTH_SHORT).show();

                return false;
            }

        });




        holder.honey_day.setText(item.getDay());
        holder.honey_time.setText(item.getTime());
        holder.honey_name.setText(item.getName());

        switch(item.getTextcolor()){
            case "Black":
                holder.honey_content.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.honey_content.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.honey_content.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.honey_content.setTextColor(Color.GREEN);
                break;
        }

        switch(item.getTitlecolor()){
            case "Black":
                holder.honey_title.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.honey_title.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.honey_title.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.honey_title.setTextColor(Color.GREEN);
                break;
        }

        switch(item.getNamecolor()){
            case "Black":
                holder.honey_name.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.honey_name.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.honey_name.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.honey_name.setTextColor(Color.GREEN);
                break;
        }

        Typeface typeface;

        switch (item.getTextfont()){
            case "sans":
                holder.honey_content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/batang.ttc");
                holder.honey_content.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/nanumpen.ttf");
                holder.honey_content.setTypeface(typeface);
                break;
        }

        switch (item.getTitlefont()){
            case "sans":
                holder.honey_title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                holder.honey_title.setTypeface(null,Typeface.BOLD);
                break;
            case "serif":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/batang.ttc");
                holder.honey_title.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/nanumpen.ttf");
                holder.honey_title.setTypeface(typeface);
                break;
        }

        switch (item.getNamefont()){
            case "sans":
                holder.honey_name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/batang.ttc");
                holder.honey_name.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(honey_context.getAssets(), "fonts/nanumpen.ttf");
                holder.honey_name.setTypeface(typeface);
                break;
        }

        if(position ==0) {
            holder.honey_content.setText(item.getContent());
            holder.honey_title.setText(item.getTitle());
        }else{
            holder.honey_content.setText(item.getContent());
            holder.honey_title.setText(item.getTitle());
            holder.honey_content.setVisibility(View.GONE);
            holder.honey_title.setVisibility(View.GONE);
        }

        holder.honey_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Honey_tip_ForDialog.class);

                intent.putExtra("day", holder.honey_day.getText());
                intent.putExtra("time", holder.honey_time.getText());
                intent.putExtra("name", holder.honey_name.getText());
                intent.putExtra("title", holder.honey_title.getText());
                intent.putExtra("content", holder.honey_content.getText());

                intent.putExtra("textsize", item.getTextsize());
                intent.putExtra("textcolor", item.getTextcolor());
                intent.putExtra("textfont", item.getTextfont());

                intent.putExtra("titlesize", item.getTitlesize());
                intent.putExtra("titlecolor", item.getTitlecolor());
                intent.putExtra("titlefont", item.getTitlefont());

                intent.putExtra("namesize", item.getNamesize());
                intent.putExtra("namecolor", item.getNamecolor());
                intent.putExtra("namefont", item.getNamefont());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.honey_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView honey_day;
        TextView honey_content;
        TextView honey_time;
        TextView honey_name;
        TextView honey_title;
        CardView honey_cardview;


        public ViewHolder(View itemView) {
            super(itemView);
            honey_time = (TextView) itemView.findViewById(R.id.honey_time);
            honey_day = (TextView) itemView.findViewById(R.id.honey_day);
            honey_name = (TextView) itemView.findViewById(R.id.honey_name);
            honey_content = (TextView) itemView.findViewById(R.id.honey_content);
            honey_title = (TextView) itemView.findViewById(R.id.honey_title);
            honey_cardview = (CardView) itemView.findViewById(R.id.honey_cardview);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, R.id.edit, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, R.id.delete, 2, "삭제");
            Edit.setOnMenuItemClickListener(onMenuItemClickListener);
            Delete.setOnMenuItemClickListener(onMenuItemClickListener);
        }

        //ContextMenuItemClick이벤트를 처리해주는 코드입니다 !
        private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                       // Toast.makeText(context, "편집 롱클릭입니다.", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.delete:
                       // Toast.makeText(context, "삭제 롱클릭입니다.", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        };


    }
}


