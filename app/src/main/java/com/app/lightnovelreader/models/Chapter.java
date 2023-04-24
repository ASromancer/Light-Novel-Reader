package com.app.lightnovelreader.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;


import util.SourceUtils;


public class Chapter {
    private int chapterId;
    private String chapterName;
    private int novelId;
    private String content;

    public Chapter() {
    }

    public Chapter(int chapterId, String chapterName, int novelId, String content) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.novelId = novelId;
        this.content = content;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
