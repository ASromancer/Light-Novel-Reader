package com.app.lightnovelreader.ui.search.viewmodel;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Filter;
import com.app.lightnovelreader.models.Novel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Novel>> novelsLiveData = new MutableLiveData<>();

    public SearchViewModel() {
        getNovels();
    }

    public LiveData<List<Novel>> getNovelsLiveData() {
        return novelsLiveData;
    }


    public void getNovels() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference novelsRef = db.collection("Novel");
        Query query = novelsRef.orderBy("novelId", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Novel> novelsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Novel novel = document.toObject(Novel.class);
                    novelsList.add(novel);
                }
                novelsLiveData.setValue(novelsList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

}
