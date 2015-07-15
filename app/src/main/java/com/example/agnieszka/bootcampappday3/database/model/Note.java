package com.example.agnieszka.bootcampappday3.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Note")
public class Note extends Model {

    @Column(name = "title")
    private String mTitle;
    @Column(name = "text")
    private String mText;
    @Column(name = "type")
    private Type mType;

    public String getText() {
        return mText;
    }

    public Type getType() {
        return mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setType(Type type) {
        mType = type;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public enum Type {
        LIST, DEFAULT, TASK
    }
}
