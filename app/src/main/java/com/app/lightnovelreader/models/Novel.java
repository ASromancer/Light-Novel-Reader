package com.app.lightnovelreader.models;
import java.util.List;
public class Novel {
    public int novelId;

    public String novelName;
    public boolean status;

    public String description;

    public String author;

    public String cover;
    public String genre;

    public Novel() {
    }

    public Novel(int novelId, String novelName, boolean status, String description, String author, String cover, String genre) {
        this.novelId = novelId;
        this.novelName = novelName;
        this.status = status;
        this.description = description;
        this.author = author;
        this.cover = cover;
        this.genre = genre;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
