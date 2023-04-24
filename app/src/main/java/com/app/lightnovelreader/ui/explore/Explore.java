package com.app.lightnovelreader.ui.explore;

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
import com.app.lightnovelreader.models.Author;
import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.Genre;
import com.app.lightnovelreader.ui.chapters.adapter.ChapterAdapter;
import com.app.lightnovelreader.ui.explore.adapter.AuthorAdapter;
import com.app.lightnovelreader.ui.explore.adapter.GenreAdapter;
import com.app.lightnovelreader.ui.explore.viewmodel.ExploreViewModel;
import com.app.lightnovelreader.ui.views.SpacingDecorator;

import java.util.ArrayList;
import java.util.List;

import util.DisplayUtils;

public class Explore extends Fragment{
    private List<Genre> genreList = new ArrayList<>();
    private List<Author> authorList = new ArrayList<>();
    private ExploreViewModel exploreViewModel;
    private GenreAdapter genreAdapter;
    private AuthorAdapter authorAdapter;
    private RecyclerView rcvGenres, rcvAuthors;


    public Explore() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exploreViewModel = new ViewModelProvider(requireActivity()).get(ExploreViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvGenres = view.findViewById(R.id.rcv_genres);
        rcvAuthors = view.findViewById(R.id.rcv_authors);
        setUpUi();
        setUpObservers();
        return view;
    }

    private void setUpObservers() {
        exploreViewModel.getGenresLiveData().observe(getViewLifecycleOwner(), genreList -> {
            genreAdapter = new GenreAdapter(genreList);
            rcvGenres.setAdapter(genreAdapter);
        });
        exploreViewModel.setGenres();

        exploreViewModel.getAuthorsLiveData().observe(getViewLifecycleOwner(), authorList -> {
            authorAdapter = new AuthorAdapter(authorList);
            rcvAuthors.setAdapter(authorAdapter);

        });
        exploreViewModel.setAuthors();
    }

    private void setUpUi() {
        rcvGenres.setLayoutManager(new GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false));
        rcvAuthors.setLayoutManager(new GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false));
    }



}
