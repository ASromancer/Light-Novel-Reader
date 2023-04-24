package com.app.lightnovelreader.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.databinding.FragmentSearchBinding;
import com.app.lightnovelreader.models.Novel;
import com.app.lightnovelreader.ui.search.adapter.SearchAdapter;
import com.app.lightnovelreader.ui.search.viewmodel.SearchViewModel;
import com.app.lightnovelreader.ui.views.SpacingDecorator;

import java.util.ArrayList;
import java.util.List;

import util.DisplayUtils;


public class Search extends Fragment implements SearchAdapter.OnSearchItemClickListener{
    private List<Novel> novellist = new ArrayList<>();
    private FragmentSearchBinding binding;
    private SearchViewModel searchViewModel;
    private SearchAdapter searchAdapter;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        DisplayUtils utils = new DisplayUtils(requireContext(), R.layout.item_novel);
        binding.novelSearchList.setLayoutManager(new GridLayoutManager(requireActivity(), utils.noOfCols()));
        binding.novelSearchList.addItemDecoration(new SpacingDecorator(utils.spacing()));
        buildRecyclerView();


        return binding.getRoot();
    }


    private void buildRecyclerView() {
        searchViewModel.getNovelsLiveData().observe(getViewLifecycleOwner(), novelsList -> {
            searchAdapter = new SearchAdapter(novelsList, this);
            binding.novelSearchList.setAdapter(searchAdapter);
            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    List<Novel> filteredlist = new ArrayList<>();
                    for (Novel item : novelsList) {
                        if (item.getNovelName().toLowerCase().contains(newText.toLowerCase())) {
                            filteredlist.add(item);
                        }
                    }
                    if (filteredlist.isEmpty()) {
                        searchAdapter.filterList(filteredlist);
                    } else {
                        searchAdapter.filterList(filteredlist);
                    }
                    return false;
                }
            });
        });
    }


    @Override
    public void OnSearchItemClick(Novel item) {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", item.getNovelId());
        controller.navigate(R.id.browse_fragment_to_details, bundle);
    }
}