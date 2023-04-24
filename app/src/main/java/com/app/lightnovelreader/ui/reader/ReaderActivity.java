package com.app.lightnovelreader.ui.reader;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.ActivityReaderBinding;
import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.ReaderTheme;
import com.app.lightnovelreader.ui.reader.adapter.PageAdapter;
import com.app.lightnovelreader.ui.reader.sheet.CustomizeReader;
import com.app.lightnovelreader.ui.reader.viewmodel.ReaderViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import util.ListUtils;

public class ReaderActivity extends AppCompatActivity implements CustomizeReader.OnOptionSelection, Toolbar.OnMenuItemClickListener {
    private final List<Chapter> chapters = new ArrayList<>();
    private ActivityReaderBinding binding;
    private PageAdapter adapter;
    private List<Chapter> chapterItems = new ArrayList<>();
    private int currentChapterId;
    private int currentNovelId;
    private boolean isLoading = false;

    private int currentChapterIndex;


    private ReaderViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReaderBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(Ranobe.getThemeMode(getApplicationContext()));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.customize.setOnMenuItemClickListener(this);
        Bundle bundle = getIntent().getExtras();
        currentChapterId = bundle.getInt("chapterId");
        currentNovelId = bundle.getInt("novelId");
        viewModel = new ViewModelProvider(this).get(ReaderViewModel.class);

        adapter = new PageAdapter(chapters);
        binding.pageList.setLayoutManager(new LinearLayoutManager(this));
        binding.pageList.setAdapter(adapter);
        binding.pageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    currentChapterIndex += 1;

                    if (currentChapterIndex < chapterItems.size()) {
                        binding.progress.show();
                        viewModel.chapter(currentNovelId, chapterItems.get(currentChapterIndex).getChapterId());
                    }
                }
            }
        });

        viewModel.getChapters().observe(this, this::setChapters);
        viewModel.getChapter().observe(this, this::setChapter);
        viewModel.chapters(currentNovelId);

    }

    private void setChapters(List<Chapter> Chapter) {
        chapterItems = ListUtils.sortById(Chapter);
        for (int i = 0; i < chapterItems.size(); i++) {
            if (chapterItems.get(i).getChapterId() == currentChapterId) {
                currentChapterIndex = i;
                break;
            }
        }
        viewModel.chapter(currentNovelId, currentChapterId);
    }

    private void setUpCustomizeReader() {
        CustomizeReader sheet = new CustomizeReader(this);
        sheet.show(getSupportFragmentManager(), "customize-sheet");
    }

    private void setChapter(Chapter chapter) {
        isLoading = false;
        binding.progress.hide();
        chapter.setChapterId(chapterItems.get(currentChapterIndex).getChapterId());
        chapters.add(chapter);
        adapter.notifyItemInserted(chapters.size() - 1);
    }


    @Override
    public void setFontSize(float size) {
        Ranobe.storeReaderFont(this, size);
        adapter.setFontSize(size);
        adapter.notifyItemRangeChanged(0, chapters.size());
    }

    @Override
    public void setReaderTheme(String themeName) {
        ReaderTheme theme = Ranobe.themes.get(themeName);
        adapter.setTheme(theme);
        adapter.notifyItemRangeChanged(0, chapters.size());
        Ranobe.storeReaderTheme(this, themeName);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.customize_settings) {
            setUpCustomizeReader();
            return true;
        }

        return false;
    }
}