package ru.gb.notes.ui;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;


public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {

    private boolean editMode = false;
    private NotesListFragment notesListFragment;
    private EditNoteFragment editNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_fragment_activity);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.EDIT_MODE)) {
            editNoteFragment = (EditNoteFragment) savedInstanceState.getSerializable(Constants.EDIT_NOTE_FRAGMENT);
            editMode = savedInstanceState.getBoolean(Constants.EDIT_MODE);
        }
        if (isLandScape()) {
            setSplitViewFragments();
        } else {
            setFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        EditNoteFragment f = (EditNoteFragment) getSupportFragmentManager().findFragmentByTag(Constants.EDIT_NOTE_FRAGMENT);
        if (f != null) {
            outState.putSerializable(Constants.EDIT_NOTE_FRAGMENT, f);
        }
        outState.putBoolean(Constants.EDIT_MODE, editMode);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private boolean isLandScape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setSplitViewFragments() {
        if (editMode) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_holder, new NotesListFragment())
                    .replace(R.id.second_fragment_holder, editNoteFragment)
                    .commit();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, new NotesListFragment())
                    .commit();

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (InMemoryRepoImpl.getInstance().getAll().size() > 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.second_fragment_holder,
                                EditNoteFragment.getInstance(InMemoryRepoImpl
                                        .getInstance().getAll().get(0)))
                        .commit();
            }
        }

    }

    private void setFragment() {
        notesListFragment = new NotesListFragment();
        if(editMode){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, editNoteFragment, Constants.EDIT_NOTE_FRAGMENT);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
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
                    .replace(R.id.second_fragment_holder, new EditNoteFragment(), Constants.EDIT_NOTE_FRAGMENT)
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_holder, new EditNoteFragment(), Constants.EDIT_NOTE_FRAGMENT)
                    .commit();
            editMode = true;
        }
    }

    @Override
    public void editNote(Note note) {
        if (isLandScape()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.second_fragment_holder, EditNoteFragment.getInstance(note), Constants.EDIT_NOTE_FRAGMENT).commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.fragment_holder, EditNoteFragment.getInstance(note), Constants.EDIT_NOTE_FRAGMENT).commit();
            editMode = true;
        }

    }

    @Override
    public void saveNote() {
        if (isLandScape()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, new NotesListFragment())
                    .commit();
        } else {
            editMode = false;
            super.onBackPressed();
        }
    }


}