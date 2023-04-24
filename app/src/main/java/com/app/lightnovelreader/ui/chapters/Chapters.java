package com.app.lightnovelreader.ui.chapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.FragmentChaptersBinding;
import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.ui.browse.adapter.NovelAdapter;
import com.app.lightnovelreader.ui.chapters.adapter.ChapterAdapter;
import com.app.lightnovelreader.ui.chapters.viewmodel.ChaptersViewModel;
import com.app.lightnovelreader.ui.details.viewmodel.DetailsViewModel;
import com.app.lightnovelreader.ui.reader.ReaderActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import util.ListUtils;

public class Chapters extends Fragment implements ChapterAdapter.OnChapterItemClickListener, Toolbar.OnMenuItemClickListener {
    private final List<Chapter> originalItems = new ArrayList<>();
    private ChaptersViewModel viewModel;
    private int novelId;
    private ChapterAdapter adapter;

    private MaterialToolbar tbToolBar;
    private TextInputLayout svSearchView;
    private TextInputEditText edtSearchField;
    private RecyclerView rcvChapterList;
    private LinearProgressIndicator progress;

    public Chapters() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            novelId = getArguments().getInt("novelId");
        }
        viewModel = new ViewModelProvider(requireActivity()).get(ChaptersViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapters, container, false);
        //intital view
        tbToolBar = view.findViewById(R.id.toolbar);
        svSearchView = view.findViewById(R.id.chapter_search_view);
        edtSearchField = view.findViewById(R.id.chapter_search_field);
        rcvChapterList = view.findViewById(R.id.chapter_list);
        progress = view.findViewById(R.id.chapter_progress);
        setUpUi();
        setUpObservers();
        return view;
    }

    private void setUpObservers() {
        viewModel.getChapterLiveData().observe(getViewLifecycleOwner(), chapterList -> {
            adapter = new ChapterAdapter(chapterList, this);
            rcvChapterList.setAdapter(adapter);
            progress.hide();
        });
        viewModel.getChapter(novelId);
    }

    private void setUpUi() {
        tbToolBar.setOnMenuItemClickListener(this::onMenuItemClick);
        edtSearchField.addTextChangedListener(new SearchBarTextWatcher());

        adapter = new ChapterAdapter(originalItems, this);
        rcvChapterList.setLayoutManager(new LinearLayoutManager(requireActivity()));

    }

    private void setUpError(String error) {
        progress.hide();
        if (originalItems.size() == 0) {
            //Error.navigateToErrorFragment(requireActivity(), error);
        }
    }

    private void searchResults(String keyword) {
        if (keyword.length() > 0) {
            List<Chapter> filtered = ListUtils.searchByName(keyword.toLowerCase(), originalItems);
            ChapterAdapter searchAdapter = new ChapterAdapter(filtered, this);
            rcvChapterList.setAdapter(searchAdapter);
        } else {
            rcvChapterList.setAdapter(adapter);
        }
    }

    private void setSearchView() {
        int mode = svSearchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
        svSearchView.setVisibility(mode);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setChapter(List<Chapter> chapters) {
        originalItems.clear();
        originalItems.addAll(chapters);
        adapter.notifyDataSetChanged();
        tbToolBar.setTitle(String.format(Locale.getDefault(), "%d Chapters", chapters.size()));
        progress.hide();
    }

    private void sort() {
        Collections.reverse(originalItems);
        adapter.notifyItemRangeChanged(0, originalItems.size());
    }

    @Override
    public void onChapterItemClick(Chapter chapter) {
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", chapter.getNovelId());
        bundle.putInt("chapterId", chapter.getChapterId());
        requireActivity().startActivity(new Intent(requireActivity(), ReaderActivity.class).putExtras(bundle));
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.sort) {
            sort();
        } else if (id == R.id.search) {
            setSearchView();
        }
        return true;
    }

    public class SearchBarTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            searchResults(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}