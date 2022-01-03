package ru.gb.notes.ui;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;

public class EditNoteActivity extends AppCompatActivity {
    private boolean updateMode;
    private EditText title;
    private EditText description;
    private Button button;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        button = findViewById(R.id.update_note_button);
        updateMode = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            updateMode = true;
            Note note = (Note) extras.getSerializable(Constants.NOTE);
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            id = note.getId();
        }
        button.setOnClickListener(x -> {
            Repo repo = InMemoryRepoImpl.getInstance();
            if (updateMode) {
                updateNote(repo);
            } else {
                createNote(repo);
            }
        });
    }

    private void createNote(Repo repo) {
        Note note = new Note(title.getText().toString(), description.getText().toString());
        repo.create(note);
    }

    private void updateNote(Repo repo) {
        Note note = new Note(id, title.getText().toString(), description.getText().toString());
        repo.update(note);
    }
}