package com.example.agnieszka.bootcampappday3.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

@Table(name = "User")
public class User extends Model {

    @Column(name = "username")
    private String mUsername;

    @Nullable
    public List<UsersNotes> getUserNotes() {
        return getMany(UsersNotes.class, "User");
    }

    public List<Note> getNotes() {
        List<UsersNotes> userNotes = getUserNotes();
        List<Note> notes = new ArrayList<>();
        for(UsersNotes usersNote : userNotes) {
            notes.add(usersNote.getNote());
        }
        return null;
    }

    public String getName() {
        return mUsername;
    }

    public void setName(String username) {
        mUsername = username;
    }
}

