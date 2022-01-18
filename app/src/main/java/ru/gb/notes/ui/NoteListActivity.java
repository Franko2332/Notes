package ru.gb.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.interfaces.ExitFromNotesController;
import ru.gb.notes.interfaces.PopupMenuItemClickListener;


public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller, PopupMenuItemClickListener, ExitFromNotesController {

    private boolean editMode = false;
    private NotesListFragment notesListFragment;
    private EditNoteFragment editNoteFragment;
    private ExitDialogFragment exitDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_fragment_activity);
        notesListFragment = new NotesListFragment();
        exitDialogFragment = new ExitDialogFragment();
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.EDIT_MODE)) {
            editNoteFragment = (EditNoteFragment) savedInstanceState.getSerializable(Constants.EDIT_NOTE_FRAGMENT);
            editMode = savedInstanceState.getBoolean(Constants.EDIT_MODE);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.NOTE_LIST_FRAGMENT)) {
            notesListFragment = (NotesListFragment) savedInstanceState.getSerializable(Constants.NOTE_LIST_FRAGMENT);
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
        NotesListFragment n = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(Constants.NOTE_LIST_FRAGMENT);
        if (f != null) {
            outState.putSerializable(Constants.EDIT_NOTE_FRAGMENT, f);
        }
        if (n != null) {
            outState.putSerializable(Constants.NOTE_LIST_FRAGMENT, n);
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
                    .replace(R.id.second_fragment_holder, editNoteFragment)
                    .commit();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, notesListFragment)
                    .commit();

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (InMemoryRepoImpl.getInstance().getAll().size() > 0) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_holder, notesListFragment)
                        .add(R.id.second_fragment_holder,
                                EditNoteFragment.getInstance(InMemoryRepoImpl
                                        .getInstance().getAll().get(0)))
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_holder, notesListFragment)
                        .commit();

            }
        }

    }

    private void setFragment() {
        notesListFragment = new NotesListFragment();
        if (editMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, editNoteFragment, Constants.EDIT_NOTE_FRAGMENT);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, notesListFragment, Constants.NOTE_LIST_FRAGMENT)
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
            notesListFragment.notifyDataSetChangedInAdapter();
        } else {
            editMode = false;
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {

        if (isItPossibleToExitTheApplication()) {
            new ExitDialogFragment().show(getSupportFragmentManager(), Constants.EXIT_DIALOG_FRAGMENT);
        } else if (editMode) {
            editMode = false;
            super.onBackPressed();
        }
    }

    private boolean isItPossibleToExitTheApplication() {
        boolean result = false;
        if (isLandScape() || !editMode) {
            result = true;
        }
        return result;
    }

    @Override
    public void click(int command, Note note, int position) {
        if (command == R.id.context_delete) {
            notesListFragment.deleteNote(note, position);
            if (isLandScape()) {
                if (InMemoryRepoImpl.getInstance().getAll().size() > 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.second_fragment_holder,
                                    EditNoteFragment.getInstance(InMemoryRepoImpl.
                                            getInstance().getAll().get(0))).commit();
                } else {
                    EditNoteFragment editNoteFragment = (EditNoteFragment) getSupportFragmentManager().
                            findFragmentByTag(Constants.EDIT_NOTE_FRAGMENT);
                    if (editNoteFragment != null) {
                        getSupportFragmentManager().beginTransaction().remove(editNoteFragment).commit();
                    }
                }
            }
        } else {
            editNote(note);
        }
    }

    @Override
    public void exit() {
        Toast.makeText(getBaseContext(), "exited the app", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void cancel() {
        exitDialogFragment = (ExitDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(Constants.EXIT_DIALOG_FRAGMENT);
        if (exitDialogFragment != null) {
            exitDialogFragment.dismiss();
            setFragment();
        }
    }


}