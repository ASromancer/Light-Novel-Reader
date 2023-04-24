package com.app.lightnovelreader.ui.genre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.databinding.ItemNovelBinding;
import com.app.lightnovelreader.models.Novel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NovelGenreAdapter extends RecyclerView.Adapter<NovelGenreAdapter.MyViewHolder> {
    private final List<Novel> items;
    private final OnNovelGenreItemClickListener listener;

    public NovelGenreAdapter(List<Novel> items, OnNovelGenreItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public List<Novel> getItems() {
        return items;
    }

    @NonNull
    @Override
    public NovelGenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNovelBinding binding = ItemNovelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NovelGenreAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelGenreAdapter.MyViewHolder holder, int position) {
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

    public interface OnNovelGenreItemClickListener {
        void onNovelGenreItemClick(Novel item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNovelBinding binding;

        public MyViewHolder(@NonNull ItemNovelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.novelCoverLayout.setOnClickListener(v ->
                    listener.onNovelGenreItemClick(items.get(getAdapterPosition())));
        }
    }
}
