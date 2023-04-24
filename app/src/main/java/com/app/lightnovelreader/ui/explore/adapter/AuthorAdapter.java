package com.app.lightnovelreader.ui.explore.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.databinding.ItemAuthorsBinding;
import com.app.lightnovelreader.models.Author;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.MyViewHolder> {
    private final List<Author> items;

    public AuthorAdapter(List<Author> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AuthorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAuthorsBinding binding = ItemAuthorsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorAdapter.MyViewHolder holder, int position) {
        Author item = items.get(position);
        holder.binding.tvAuthor.setText(item.getAuthorName());
        holder.binding.authorItemLayout.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_main);
            Bundle bundle = new Bundle();
            bundle.putString("author", item.getAuthorName());
            controller.navigate(R.id.explore_fragment_to_author_fragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemAuthorsBinding binding;

        public MyViewHolder(@NonNull ItemAuthorsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
