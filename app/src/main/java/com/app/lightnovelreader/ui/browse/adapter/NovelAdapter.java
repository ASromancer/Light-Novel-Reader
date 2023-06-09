package com.app.lightnovelreader.ui.browse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.databinding.ItemNovelBinding;
import com.app.lightnovelreader.models.Novel;
import com.bumptech.glide.Glide;

import java.util.List;


public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.MyViewHolder> {
    private final List<Novel> items;
    private final OnNovelItemClickListener listener;

    public NovelAdapter(List<Novel> items, OnNovelItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public List<Novel> getItems() {
        return items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNovelBinding binding = ItemNovelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Novel item = items.get(position);
        holder.binding.novelName.setText(item.getNovelName());
        Glide.with(holder.binding.novelCover.getContext())
                .load(item.getCover())
                .into(holder.binding.novelCover);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnNovelItemClickListener {
        void onNovelItemClick(Novel item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNovelBinding binding;

        public MyViewHolder(@NonNull ItemNovelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.novelCoverLayout.setOnClickListener(v ->
                    listener.onNovelItemClick(items.get(getAdapterPosition())));
        }
    }
}



