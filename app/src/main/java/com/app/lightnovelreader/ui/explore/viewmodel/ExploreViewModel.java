package com.app.lightnovelreader.ui.explore.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lightnovelreader.models.Author;
import com.app.lightnovelreader.models.Chapter;
import com.app.lightnovelreader.models.Genre;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreViewModel extends ViewModel {
    private MutableLiveData<List<Genre>> genres;
    private MutableLiveData<List<Author>> authors;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<List<Genre>> getGenresLiveData() {
        if (genres == null) {
            genres = new MutableLiveData<>();
        }
        return genres;
    }

    public MutableLiveData<List<Author>> getAuthorsLiveData() {
        if (authors == null) {
            authors = new MutableLiveData<>();
        }
        return authors;
    }

    public void setGenres() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference genreRef =  db.collection("Genre");
        genreRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Genre> genreList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Genre genre = document.toObject(Genre.class);
                    genreList.add(genre);
                }
                genres.setValue(genreList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public void setAuthors() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference authorRef =  db.collection("Author");
        authorRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Author> authorList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Author author = document.toObject(Author.class);
                    authorList.add(author);
                }
                authors.setValue(authorList);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}
