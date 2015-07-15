package com.example.agnieszka.bootcampappday3.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.activeandroid.Model;
import com.example.agnieszka.bootcampappday3.R;
import com.example.agnieszka.bootcampappday3.database.DatabaseHelper;
import com.example.agnieszka.bootcampappday3.database.model.Note;
import com.example.agnieszka.bootcampappday3.database.model.User;
import com.example.agnieszka.bootcampappday3.database.model.UsersNotes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text_note)
    TextView mNoteTextView;
    private List<Model> mUsers;
    private List<Model> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        saveUsersNotes();
    }

    private void saveUsersNotes() {
        new SaveUsersTask().execute();
    }

    private class SaveUsersTask extends AsyncTask<Void, Void, List<Model>> {

        @Override
        protected List<Model> doInBackground(Void... params) {
            List<Model> users = createUsers();
            if (DatabaseHelper.getInstance().save(users)) {
                return users;
            }
            return null;
        }

        private List<Model> createUsers() {
            List<Model> users = new ArrayList<>();
            for(int i=0; i< 3 ; i++) {
                User user = new User();
                user.setName("Agnieszka" + i);
            }
            return users;
        }

        @Override
        protected void onPostExecute(@Nullable List<Model> users) {
            super.onPostExecute(users);
            if (users != null) {
                mUsers = users;
                new SaveNotesTask().execute(mUsers);
            }
        }
    }

    private class SaveNotesTask extends AsyncTask<List<Model>, Void, List<Model>> {

        @Override
        protected List<Model> doInBackground(List<Model>... users) {
            List<Model> notes = createNotes();
            if (DatabaseHelper.getInstance().save(notes)) {
                return notes;
            }
            return null;
        }

        private List<Model> createNotes() {
            List<Model> notes = new ArrayList<>();
            for(int i=0; i< 3 ; i++) {
                Note note = new Note();
                note.setTitle("Note" + i);
                note.setText("New note text" + i);
                note.setType(Note.Type.DEFAULT);
                notes.add(note);
            }
            return notes;
        }

        @Override
        protected void onPostExecute(@Nullable List<Model> notes) {
            super.onPostExecute(notes);
            if (notes != null) {
                mNotes = notes;
                mNoteTextView.setText("Notes: " + mNotes);
                    //new SaveUsersNotesTask().execute(mUsers, mNotes);
            } else
                mNoteTextView.setText("Something went wrong...");
        }
    }
//nie zdazylam reszty
//    private class SaveUsersNotesTask extends AsyncTask<List<Model>, Void, UsersNotes> {

//        @Override
//        protected List<Model> doInBackground(List<Model>... models) {
//            List<Model> usersNotes = createUsersNotes((List<Model>) models[0], (List<Model>) models[1]);
//            if (DatabaseHelper.getInstance().save(usersNotes)) {
//
//                mNoteTextView.setText("Notes: " + mNotes);
//                return usersNotes;
//            }
//            return null;
//        }
//
//        private List<Model> createUsersNotes(List<Model> users, List<Model> notes) {
//            List<Model> usersNotes = new ArrayList<>();
//            //nie zdazylam
//            return null;
//        }
//    }
}