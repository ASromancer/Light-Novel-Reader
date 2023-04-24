package com.app.lightnovelreader.ui.chapters.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.Novel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChaptersViewModel extends ViewModel {
    private final MutableLiveData<List<Chapter>> chapterLiveData = new MutableLiveData<>();

    public void getChapter(int novelId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference chapterRef =  db.collection("Chapter");
        Query query = chapterRef.whereEqualTo("novelId", novelId).orderBy("chapterId", Query.Direction.ASCENDING);
        query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Chapter> chapterList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Chapter chapter = document.toObject(Chapter.class);
                            chapterList.add(chapter);
                        }
                        chapterLiveData.setValue(chapterList);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public LiveData<List<Chapter>> getChapterLiveData() {
        return chapterLiveData;
    }

}
