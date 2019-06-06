package com.enderstudy.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder> {

    private final LayoutInflater inflater;
    private List<ListItem> listItems;
    private ListItemViewHolder activeViewHolder;

    ListItemAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        activeViewHolder = holder;

        if(listItems != null) {
            ListItem current = listItems.get(position);
            holder.listItemItemView.setText(current.getName());
        } else {
            holder.listItemItemView.setText("Name missing!");
        }
    }

    void setListItems(List<ListItem> items) {
        listItems = items;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(listItems != null) {
            return listItems.size();
        }
        else return 0;
    }

    public ListItem getListItemAtPosition(int position) {
        return listItems.get(position);
    }

    public void markInBasket(int position) {
        String newText = activeViewHolder.listItemItemView.getText() + " In Basket!";
        activeViewHolder.listItemItemView.setTextColor(Color.parseColor("#FF0000"));
        activeViewHolder.listItemItemView.setText(newText);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemItemView;

        private ListItemViewHolder(View itemView) {
            super(itemView);
            listItemItemView = itemView.findViewById(R.id.textView);
        }
    }
}
