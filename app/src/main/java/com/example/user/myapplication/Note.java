package com.example.user.myapplication;

import java.io.Serializable;

/**
 * Created by User on 06/10/2016.
 */
public class Note implements Serializable
{
    private String title;
    private String content;
    private String fileName;

    public Note(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return title;
    }
}
