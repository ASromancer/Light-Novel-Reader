package com.app.lightnovelreader.ui.author;

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
import com.app.lightnovelreader.ui.browse.adapter.NovelAdapter;
import com.app.lightnovelreader.ui.browse.viewmodel.BrowseViewModel;
import com.app.lightnovelreader.ui.views.SpacingDecorator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import util.DisplayUtils;

public class NovelAuthor extends Fragment implements NovelAuthorAdapter.OnNovelAuthorItemClickListener {

    private List<Novel> novelList = new ArrayList<>();
    private NovelAuthorViewModel novelAuthorViewModel;
    private NovelAuthorAdapter novelAuthorAdapter;
    private RecyclerView recyclerView;
    private LinearProgressIndicator progress;
    private String author;
    private boolean isLoading = false;

    public NovelAuthor() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            author = getArguments().getString("author");
        }
        novelAuthorViewModel = new ViewModelProvider(requireActivity()).get(NovelAuthorViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author, container, false);
        DisplayUtils utils = new DisplayUtils(requireContext(), R.layout.item_novel);
        recyclerView = view.findViewById(R.id.novel_author_list);
        progress = view.findViewById(R.id.novel_author_progress);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), utils.noOfCols()));
        recyclerView.addItemDecoration(new SpacingDecorator(utils.spacing()));

        novelAuthorViewModel.getNovelsLiveData().observe(getViewLifecycleOwner(), novelsList -> {
            novelAuthorAdapter = new NovelAuthorAdapter(novelsList, this);
            recyclerView.setAdapter(novelAuthorAdapter);
            progress.hide();
        });
        novelAuthorViewModel.getNovels(author);
        return view;
    }

    @Override
    public void onNovelAuthorItemClick(Novel item) {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", item.getNovelId());
        controller.navigate(R.id.author_fragment_to_details, bundle);
    }
}