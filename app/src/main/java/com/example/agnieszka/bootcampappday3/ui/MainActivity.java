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
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text_user)
    TextView mUserTextView;
    private List<Model> mUsers;
    private List<Model> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        saveUsers();
    }

    @OnClick(R.id.add_button)
    public void onClick() {
        saveUsers();
    }

    private void saveUsers() {
        new SaveUsersTask().execute();
    }

    private class SaveUsersTask extends AsyncTask<Void, Void, List<Model>> {

        @Override
        protected List<Model> doInBackground(Void... params) {
            List<Model> users = createUsers();
            if (DatabaseHelper.getInstance().saveUsers(users)) {
                return users;
            }
            return null;
        }

        private List<Model> createUsers() {
            List<Model> users = new ArrayList<>();

            User user = new User();
            user.setName("Agnieszka");
            users.add(user);

            User user1 = new User();
            user1.setName("Kamil");
            users.add(user1);

            User user2 = new User();
            user2.setName("Kacper");
            users.add(user2);

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
            if (DatabaseHelper.getInstance().saveNotes(notes)) {
                return notes;
            }
            return null;
        }

        private List<Model> createNotes() {
            List<Model> notes = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Note note = new Note();
                note.setTitle("NoteTitle" + i);
                note.setText("Note nr " + i);
                if (i % 2 == 0) {
                    note.setType(Note.Type.DEFAULT);
                } else {
                    note.setType(Note.Type.LIST);
                }
                notes.add(note);
            }
            return notes;
        }

        @Override
        protected void onPostExecute(@Nullable List<Model> notes) {
            super.onPostExecute(notes);
            if (notes != null) {
                mNotes = notes;
                new SaveUsersNotesTask().execute(mUsers, mNotes);
            }
        }
    }

    private class SaveUsersNotesTask extends AsyncTask<List<Model>, Void, List<UsersNotes>> {

        @Override
        protected List<UsersNotes> doInBackground(List<Model>... models) {
            List<Model> users = models[0];
            List<Model> notes = models[1];
            List<UsersNotes> usersNotes = new ArrayList<>();

            int userPositionOnList = 0;
            for (Model note : notes) {
                Model user = users.get(userPositionOnList);

                usersNotes.add(new UsersNotes(user, note));

                userPositionOnList++;
                userPositionOnList = userPositionOnList % users.size();
            }

            if (DatabaseHelper.getInstance().saveUsersNotes(usersNotes)) {
                return usersNotes;
            }
            return null;
        }

        @Override
        protected void onPostExecute(@Nullable List<UsersNotes> usersNotes) {
            super.onPostExecute(usersNotes);
            mUserTextView.setText("");

            if (usersNotes != null) {
                mUserTextView.append("Users and their notes: \n\n");
                List<User> userList = DatabaseHelper.getInstance().getAllUsers();
                for (User user : userList) {
                    mUserTextView.append(user.getName() + ":\n");
                    if (user.getNotes() != null && !user.getNotes().isEmpty()) {
                        for (Note note : user.getNotes()) {
                            mUserTextView.append(note.getTitle() + ", " +
                                    note.getText() + ", " +
                                    note.getType() + "\n");
                        }
                    }
                    mUserTextView.append("\n");
                }
            } else {
                mUserTextView.setText("Something went wrong...");
            }
        }
    }
}