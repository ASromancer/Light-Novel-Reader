package com.app.lightnovelreader.ui.browse.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Novel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BrowseViewModel extends ViewModel {
    private MutableLiveData<List<Novel>> novelsLiveData = new MutableLiveData<>();

    public BrowseViewModel() {
        getNovels();
    }

    public LiveData<List<Novel>> getNovelsLiveData() {
        return novelsLiveData;
    }


    public void getNovels() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Integer> values = Arrays.asList(1, 2);
        CollectionReference novelsRef = db.collection("Novel");
        Query query = novelsRef.whereIn("novelId", values);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Novel> novelsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Novel novel = document.toObject(Novel.class);
                    novelsList.add(novel);
                    Log.i("NOVEL:", novel.getNovelName());
                }
                novelsLiveData.setValue(novelsList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

}

