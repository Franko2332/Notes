package ru.gb.notes.ui;

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
        setFragment();
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
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.fragment_holder, new EditNoteFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void editNote(Note note) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_holder, EditNoteFragment.getInstance(note)).commit();
    }

    @Override
    public void saveNote() {
        super.onBackPressed();
    }

}