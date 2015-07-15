package com.example.agnieszka.bootcampappday3.database;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
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

    public boolean save(Note note) {
        return note.save() > -1;
    }

    public boolean save(List<Model> notes) {
        try {
            ActiveAndroid.beginTransaction();
            for (Model note : notes) {
                if (!save((Note)note)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public boolean save(User user) {
        return user.save() > -1;
    }

    public boolean saveUser(List<Model> users) {
        try {
            ActiveAndroid.beginTransaction();
            for (Model user : users) {
                if (!save((User)user)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public boolean save(UsersNotes usersNotes) {
        return usersNotes.save() > -1;
    }

    public boolean saveUsersNotes(List<Model> usersNotes) {
        try {
            ActiveAndroid.beginTransaction();
            for (Model usersNote : usersNotes) {
                if (!save((UsersNotes)usersNotes)) {
                    return false;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

}


