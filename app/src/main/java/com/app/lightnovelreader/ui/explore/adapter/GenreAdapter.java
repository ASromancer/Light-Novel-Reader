package com.app.lightnovelreader.ui.explore.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.databinding.ItemGenresBinding;
import com.app.lightnovelreader.models.Genre;


import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    private final List<Genre> items;
    public GenreAdapter(List<Genre> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGenresBinding binding = ItemGenresBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.MyViewHolder holder, int position) {
        Genre item = items.get(position);
        holder.binding.tvGenre.setText(item.getGenreName());
        holder.binding.genreItemLayout.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_main);
            Bundle bundle = new Bundle();
            bundle.putString("genre", item.getGenreName());
            controller.navigate(R.id.explore_fragment_to_genre_fragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnGenreItemClickListener {
        void OnGenreItemClick(Genre item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemGenresBinding binding;

        public MyViewHolder(@NonNull ItemGenresBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

