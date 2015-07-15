package com.example.agnieszka.bootcampappday3.database;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.agnieszka.bootcampappday3.database.model.Note;
import com.example.agnieszka.bootcampappday3.database.model.User;
import com.example.agnieszka.bootcampappday3.database.model.UsersNotes;

import java.util.List;

public class DatabaseHelper {

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper() {
    }

    public static DatabaseHelper getInstance() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper();
        }

        return databaseHelper;
    }

    public boolean saveNote(Note note) {
        return note.save() > -1;
    }

    public boolean saveNotes(List<Model> notes) {
        try {
            ActiveAndroid.beginTransaction();
            for (Model note : notes) {
                if (!saveNote((Note) note)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public boolean saveUser(User user) {
        return user.save() > -1;
    }

    public boolean saveUsers(List<Model> users) {
        try {
            ActiveAndroid.beginTransaction();
            for (Model user : users) {
                if (!saveUser((User) user)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public boolean saveUsersNote(UsersNotes usersNote) {
        return usersNote.save() > -1;
    }

    public boolean saveUsersNotes(List<UsersNotes> usersNotes) {
        try {
            ActiveAndroid.beginTransaction();
            for (UsersNotes usersNote : usersNotes) {
                if (!saveUsersNote(usersNote)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public List<User> getAllUsers() {
        return new Select().from(User.class).execute();
    }

}


