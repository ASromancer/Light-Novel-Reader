package com.app.lightnovelreader.ui.reader.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LineHeightSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.App;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.ItemPageBinding;
import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.ReaderTheme;
import com.google.android.material.color.MaterialColors;


import java.util.List;
import java.util.Locale;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.MyViewHolder> {
    private final List<Chapter> chapters;
    private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    private ReaderTheme theme;
    private float fontSize;

    public PageAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
        this.theme = Ranobe.themes.get(Ranobe.getReaderTheme(App.getContext()));
        this.fontSize = Ranobe.getReaderFont(App.getContext());
    }

    public void setTheme(ReaderTheme theme) {
        this.theme = theme;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPageBinding binding = ItemPageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);

        if (theme != null) {
            holder.binding.pageLayout.setBackgroundColor(theme.getBackground());
            holder.binding.content.setTextColor(theme.getText());
        } else {
            holder.binding.pageLayout.setBackgroundColor(
                    getThemeColor(holder.binding.pageLayout, com.google.android.material.R.attr.colorSurface)
            );
            holder.binding.content.setTextColor(
                    getThemeColor(holder.binding.content, com.google.android.material.R.attr.colorOnSurface)
            );
        }

        holder.binding.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        holder.binding.chapterTitle.setText(chapter.getChapterName());
        String content = chapter.getContent();
        SpannableString spannableString = new SpannableString(content);
        int index = content.indexOf("\n");
        if (index >= 0) {
            spannableString.setSpan(new LineHeightSpan.Standard(1), index, index + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        holder.binding.content.setText(spannableString);
        holder.binding.pageLayout.setLayoutParams(params);
        holder.binding.content.setLayoutParams(params);
    }

    private int getThemeColor(View view, int attr) {
        return MaterialColors.getColor(view, attr);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemPageBinding binding;

        public MyViewHolder(@NonNull ItemPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
