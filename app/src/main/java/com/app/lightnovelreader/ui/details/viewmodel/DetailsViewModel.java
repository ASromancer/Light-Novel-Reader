package com.app.lightnovelreader.ui.details.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Novel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetailsViewModel extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<DocumentSnapshot> novelLiveData = new MutableLiveData<>();

    public void getNovel(int novelId) {
        db.collection("Novel")
                .whereEqualTo("novelId", novelId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        novelLiveData.setValue(task.getResult().getDocuments().get(0));
                    } else {
                        novelLiveData.setValue(null);
                    }
                });
    }

    public LiveData<DocumentSnapshot> getNovelLiveData() {
        return novelLiveData;
    }
}
