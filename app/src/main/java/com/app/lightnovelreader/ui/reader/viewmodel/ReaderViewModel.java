package com.app.lightnovelreader.ui.reader.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.Novel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReaderViewModel extends ViewModel {
    private MutableLiveData<Chapter> chapter;
    private MutableLiveData<List<Chapter>> chapters;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<Chapter> getChapter() {
        if (chapter == null) {
            chapter = new MutableLiveData<>();
        }
        return chapter;
    }

    public MutableLiveData<List<Chapter>> getChapters() {
        if (chapters == null) {
            chapters = new MutableLiveData<>();
        }
        return chapters;
    }

    public void chapter(int novelId, int chapterId) {
        CollectionReference chapterRef =  db.collection("Chapter");
        Query query = chapterRef.whereEqualTo("chapterId", chapterId).whereEqualTo("novelId", novelId);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                Chapter chapterObject = document.toObject(Chapter.class);
                chapter.setValue(chapterObject);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public void chapters(int novelId) {
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
                chapters.setValue(chapterList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

    }

}
