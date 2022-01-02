package ru.gb.notes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.Note;

public class EditNoteActivity extends AppCompatActivity {
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
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            Note note = (Note) extras.getSerializable(Constants.NOTE);
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            id = note.getId();
        }
    }
}