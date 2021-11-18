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

import com.example.pocket_kitchen.ui.dialogs.My_Recipe_ForDialog;
import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.My_Recipe_Item;

import java.util.List;


public class My_Recipe_RecyclerAdapter extends RecyclerView.Adapter<My_Recipe_RecyclerAdapter.ViewHolder> {
    Context context;
    List<My_Recipe_Item> items;
    int item_layout;
    Activity activity;


    public My_Recipe_RecyclerAdapter(Context context, List<My_Recipe_Item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    public My_Recipe_RecyclerAdapter(List<My_Recipe_Item> items) {
        this.items = items;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recipe_item_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final My_Recipe_Item item = items.get(position);

        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


//                Toast.makeText(context, "long Click", Toast.LENGTH_SHORT).show();

                return false;
            }

        });




        holder.day.setText(item.getDay());
        holder.time.setText(item.getTime());
        holder.name.setText(item.getName());

        switch(item.getTextcolor()){
            case "Black":
                holder.content.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.content.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.content.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.content.setTextColor(Color.GREEN);
                break;
        }

        switch(item.getTitlecolor()){
            case "Black":
                holder.title.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.title.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.title.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.title.setTextColor(Color.GREEN);
                break;
        }

        switch(item.getNamecolor()){
            case "Black":
                holder.name.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.name.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.name.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.name.setTextColor(Color.GREEN);
                break;
        }

        Typeface typeface;

        switch (item.getTextfont()){
            case "sans":
                holder.content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                holder.content.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                holder.content.setTypeface(typeface);
                break;
        }

        switch (item.getTitlefont()){
            case "sans":
                holder.title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                holder.title.setTypeface(null,Typeface.BOLD);
                break;
            case "serif":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                holder.title.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                holder.title.setTypeface(typeface);
                break;
        }

        switch (item.getNamefont()){
            case "sans":
                holder.name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                holder.name.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                holder.name.setTypeface(typeface);
                break;
        }

        if(position ==0) {
            holder.content.setText(item.getContent());
            holder.title.setText(item.getTitle());
        }else{
            holder.content.setText(item.getContent());
            holder.title.setText(item.getTitle());
            holder.content.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), My_Recipe_ForDialog.class);

                intent.putExtra("day", holder.day.getText());
                intent.putExtra("time", holder.time.getText());
                intent.putExtra("name", holder.name.getText());
                intent.putExtra("title", holder.title.getText());
                intent.putExtra("content", holder.content.getText());

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
        return this.items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView day;
        TextView content;
        TextView time;
        TextView name;
        TextView title;
        CardView cardview;


        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            day = (TextView) itemView.findViewById(R.id.day);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            title = (TextView) itemView.findViewById(R.id.title);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
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


