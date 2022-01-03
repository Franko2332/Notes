package ru.gb.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillRepo();
        init();
    }


    @Override
    protected void onResume() {
        notesAdapter.setNotes(repository.getAll());
        list.setAdapter(notesAdapter);
        Log.e("onResume", "onResume");
        super.onResume();
    }

    private void init() {
        notesAdapter = new NotesAdapter();
        notesAdapter.setNotes(repository.getAll());
        notesAdapter.setOnNoteClickListener(this);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(notesAdapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(x->{Intent intent= new Intent(this, EditNoteActivity.class);
        startActivity(intent);});
    }


    private void fillRepo() {
        for (int i = 1; i < 5 ; i++) {
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