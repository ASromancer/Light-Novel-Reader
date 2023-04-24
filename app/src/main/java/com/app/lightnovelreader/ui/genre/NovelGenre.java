package com.app.lightnovelreader.ui.genre;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.models.Novel;
import com.app.lightnovelreader.ui.author.adapter.NovelAuthorAdapter;
import com.app.lightnovelreader.ui.author.viewmodel.NovelAuthorViewModel;
import com.app.lightnovelreader.ui.genre.adapter.NovelGenreAdapter;
import com.app.lightnovelreader.ui.genre.viewmodel.NovelGenreViewModel;
import com.app.lightnovelreader.ui.views.SpacingDecorator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import util.DisplayUtils;

public class NovelGenre extends Fragment implements NovelGenreAdapter.OnNovelGenreItemClickListener {

    private List<Novel> novelList = new ArrayList<>();
    private NovelGenreViewModel novelGenreViewModel;
    private NovelGenreAdapter novelGenreAdapter;
    private RecyclerView recyclerView;
    private LinearProgressIndicator progress;
    private String author;
    private boolean isLoading = false;

    public NovelGenre() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            author = getArguments().getString("genre");
        }
        novelGenreViewModel = new ViewModelProvider(requireActivity()).get(NovelGenreViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        DisplayUtils utils = new DisplayUtils(requireContext(), R.layout.item_novel);
        recyclerView = view.findViewById(R.id.novel_genre_list);
        progress = view.findViewById(R.id.novel_genre_progress);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), utils.noOfCols()));
        recyclerView.addItemDecoration(new SpacingDecorator(utils.spacing()));

        novelGenreViewModel.getNovelsLiveData().observe(getViewLifecycleOwner(), novelsList -> {
            novelGenreAdapter = new NovelGenreAdapter(novelsList, this);
            recyclerView.setAdapter(novelGenreAdapter);
            progress.hide();
        });
        novelGenreViewModel.getNovels(author);
        return view;
    }

    @Override
    public void onNovelGenreItemClick(Novel item) {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", item.getNovelId());
        controller.navigate(R.id.genre_fragment_to_details, bundle);
    }
}