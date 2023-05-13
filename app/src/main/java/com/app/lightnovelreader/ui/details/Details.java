package com.app.lightnovelreader.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.FragmentDetailsBinding;
import com.app.lightnovelreader.models.Novel;
import com.app.lightnovelreader.ui.details.viewmodel.DetailsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details extends Fragment {
    private DetailsViewModel viewModel;
    private int novelId;

    private LinearProgressIndicator progress;
    private ImageView ivNovelCover;
    private TextView tvNovelName, tvAuthor, tvStatus, tvGenre, tvDescription;
    private Button btnReadChapter, btnFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            novelId = getArguments().getInt("novelId");
        }
        viewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        progress = view.findViewById(R.id.progress );
        ivNovelCover = view.findViewById(R.id.novel_detail_cover);
        tvNovelName = view.findViewById(R.id.novel_detail_name);
        tvAuthor = view.findViewById(R.id.detail_author);
        tvStatus = view.findViewById(R.id.detail_status);
        tvGenre = view.findViewById(R.id.detail_genre);
        tvDescription = view.findViewById(R.id.detail_description);
        btnReadChapter = view.findViewById(R.id.read_chapter);
        btnFavorite = view.findViewById(R.id.btn_favorite);
        setUpListeners();
        setUpObservers();
        return view;
    }

    private void setUpListeners() {
        btnReadChapter.setOnClickListener(v -> navigateToChapterList());
        btnFavorite.setOnClickListener(v -> addToFavorite(novelId));
        getFavoriteList();
        progress.show();
    }

    private void getFavoriteList() {

    }

    private void addToFavorite(int novelId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(uid);
        Map<String, Object> newData = new HashMap<>();
        newData.put("favorite", FieldValue.arrayUnion(novelId));
        docRef.update(newData);
    }

    private void setUpObservers() {
        // Observe the LiveData for changes and update the UI accordingly
        viewModel.getNovelLiveData().observe(getViewLifecycleOwner(), documentSnapshot -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                Novel novel = documentSnapshot.toObject(Novel.class);
                if (novel != null) {
                    RequestManager requestManager = Glide.with(this);
                    requestManager.load(novel.getCover()).into(ivNovelCover);
                    tvNovelName.setText(novel.getNovelName());
                    if(novel.isStatus() == true) {
                        tvStatus.setText("Full");
                    }
                    else {
                        tvStatus.setText("Continue");
                    }
                    tvGenre.setText(novel.getGenre());
                    tvAuthor.setText(novel.getAuthor());
                    tvDescription.setText(novel.getDescription());
                    progress.hide();
                }
            } else {
                progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Novel not found", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getNovel(novelId);
    }

    private void navigateToChapterList() {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putInt("novelId", novelId);
        controller.navigate(R.id.details_fragment_to_chapters, bundle);
    }




}
