package com.apps.kunalfarmah.furnituremagic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.kunalfarmah.furnituremagic.R;
import com.apps.kunalfarmah.furnituremagic.Room.Furniture;

import java.util.List;

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder> {
    Context mContext;
    List<Furniture> data;

    public FurnitureAdapter(Context mContext, List<Furniture> list) {
        this.mContext = mContext;
        data = list;
    }

    @NonNull
    @Override
    public FurnitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FurnitureViewHolder(inflater.inflate(R.layout.furniture_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FurnitureViewHolder holder, int position) {
        final Furniture post = data.get(position);

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public Furniture getItem(int position) {
        return data.get(position);
    }


    public class FurnitureViewHolder extends RecyclerView.ViewHolder {
        public TextView name,price,disc_price;
        public Spinner type;

        public FurnitureViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
