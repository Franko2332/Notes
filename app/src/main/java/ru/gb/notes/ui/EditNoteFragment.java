package ru.gb.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;

public class EditNoteFragment extends Fragment implements View.OnClickListener {
    private Note note;
    private EditText title;
    private EditText description;
    private Button saveButton;
    private boolean editMode;
    private Repo repo = InMemoryRepoImpl.getInstance();
    private NotesListFragment.Controller controller;

    public static EditNoteFragment getInstance(Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.NOTE, note);
        editNoteFragment.setArguments(bundle);
        return editNoteFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        controller = (NotesListFragment.Controller) getActivity();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateInit();

    }

    private void onCreateInit() {
        editMode = false;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getSerializable(Constants.NOTE) != null) {
            note = (Note) bundle.getSerializable(Constants.NOTE);
            editMode = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_or_add_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreatedInit(view);
    }

    private void onViewCreatedInit(@NonNull View view) {
        title = view.findViewById(R.id.title_edit_note);
        description = view.findViewById(R.id.description_edit_note);
        if (editMode) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
        saveButton = view.findViewById(R.id.button_update_note);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (editMode) {
            note.setTitle(title.getText().toString());
            note.setDescription(description.getText().toString());
            repo.update(note);
        } else {
            note = new Note(title.getText().toString(), description.getText().toString());
            repo.create(note);
        }
        controller.saveNote();
    }
}