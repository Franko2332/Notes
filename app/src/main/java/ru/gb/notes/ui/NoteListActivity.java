package ru.gb.notes.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;
import ru.gb.notes.recycler.NotesAdapter;

public class NoteListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {
    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillRepo();
        notesAdapter = new NotesAdapter();
        notesAdapter.setNotes(repository.getAll());
        notesAdapter.setOnNoteClickListener(this);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(notesAdapter);

    }

    private void fillRepo() {
        for (int i = 1; i < 20 ; i++) {
            repository.create(new Note("Title "+String.valueOf(i), "Description "+String.valueOf(i)));
        }
    }

    @Override
    public void onNoteClickListener(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(Constants.NOTE, note);
        startActivity(intent);
    }
}