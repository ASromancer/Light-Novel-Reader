package com.app.lightnovelreader.ui.genre.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Novel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NovelGenreViewModel extends ViewModel {
    private MutableLiveData<List<Novel>> novelsLiveData = new MutableLiveData<>();

    public LiveData<List<Novel>> getNovelsLiveData() {
        return novelsLiveData;
    }


    public void getNovels(String genre) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference novelsRef = db.collection("Novel");
        Query query = novelsRef.whereEqualTo("genre", genre);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Novel> novelsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Novel novel = document.toObject(Novel.class);
                    novelsList.add(novel);
                    Log.i("NOVEL", novel.getNovelName());
                }
                novelsLiveData.setValue(novelsList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}
