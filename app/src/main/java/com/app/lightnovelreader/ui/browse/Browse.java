package com.app.lightnovelreader.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.models.Novel;
import com.app.lightnovelreader.ui.browse.adapter.NovelAdapter;
import com.app.lightnovelreader.ui.browse.viewmodel.BrowseViewModel;
import com.app.lightnovelreader.ui.views.SpacingDecorator;
import com.google.android.material.progressindicator.LinearProgressIndicator;


import java.util.ArrayList;
import java.util.List;
import util.DisplayUtils;

public class Browse extends Fragment implements NovelAdapter.OnNovelItemClickListener {
    private List<Novel> novelList = new ArrayList<>();
    private BrowseViewModel novelViewModel;
    private NovelAdapter novelAdapter;
    private RecyclerView recyclerView;
    private LinearProgressIndicator progress;
    private boolean isLoading = false;

    public Browse() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        novelViewModel = new ViewModelProvider(requireActivity()).get(BrowseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        DisplayUtils utils = new DisplayUtils(requireContext(), R.layout.item_novel);
        recyclerView = view.findViewById(R.id.novel_list);
        progress = view.findViewById(R.id.progress);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), utils.noOfCols()));
        recyclerView.addItemDecoration(new SpacingDecorator(utils.spacing()));

        novelViewModel.getNovelsLiveData().observe(getViewLifecycleOwner(), novelsList -> {
            novelAdapter = new NovelAdapter(novelsList, this);
            recyclerView.setAdapter(novelAdapter);
            progress.hide();
        });
        novelViewModel.getNovels();
        return view;
    }

    @Override
    public void onNovelItemClick(Novel item) {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", item.getNovelId());
        controller.navigate(R.id.browse_fragment_to_details, bundle);
    }
    
}
