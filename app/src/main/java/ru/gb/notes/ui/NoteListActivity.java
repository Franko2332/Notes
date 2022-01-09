package ru.gb.notes.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;


public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {
    private NotesListFragment notesListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_fragment_activity);
        if (isLandScape()) {
            setSplitViewFragments();
        } else {
            setFragment();
        }
    }

    private boolean isLandScape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setSplitViewFragments() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, new NotesListFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.second_fragment_holder, new EditNoteFragment()).commit();
        }
    }

    private void setFragment() {
        notesListFragment = new NotesListFragment();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, notesListFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void addNote() {
        if (isLandScape()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.second_fragment_holder, new EditNoteFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).
                    replace(R.id.fragment_holder, new EditNoteFragment())
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public void editNote(Note note) {
        if (isLandScape()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.second_fragment_holder, EditNoteFragment.getInstance(note)).commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.fragment_holder, EditNoteFragment.getInstance(note)).commit();
        }
    }

    @Override
    public void saveNote() {
        if (isLandScape()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, new NotesListFragment())
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

}