package com.enderstudy.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        activeViewHolder = holder;

        if(listItems != null) {
            ListItem current = listItems.get(position);
            holder.nameTextView.setText(current.getName());
            holder.descriptionTextView.setText(current.getDescription());
            holder.inBasketCheckbox.setChecked(current.getInBasket());
        } else {
            holder.nameTextView.setText(R.string.list_name_missing_text);
            holder.descriptionTextView.setText(R.string.list_description_missing_text);
            holder.inBasketCheckbox.setChecked(false);
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

    public ListItem markInBasket(int position) {
        ListItem item = listItems.get(position);
        Boolean inBasket = item.getInBasket();
        Boolean newValue = inBasket ^= true;

        item.setInBasket(newValue);
        notifyItemChanged(position);

        return item;
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView descriptionTextView;
        private final CheckBox inBasketCheckbox;

        private ListItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
            inBasketCheckbox = itemView.findViewById(R.id.checkbox_inBasket);
        }
    }
}
