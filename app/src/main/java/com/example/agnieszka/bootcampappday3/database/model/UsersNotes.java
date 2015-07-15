package com.example.agnieszka.bootcampappday3.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "UsersNotes")
public class UsersNotes extends Model {

    @Column(name = "user")
    private User mUser;
    @Column(name = "note")
    private Note mNote;

    public UsersNotes() {

    }

    public UsersNotes(Model user, Model note) {
        mUser = (User) user;
        mNote = (Note) note;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note note) {
        mNote = note;
    }

}
