package ru.gb.notes.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.Date;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;

public class EditNoteFragment extends Fragment implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {
    private Note note;
    private EditText title;
    private EditText description;
    private Spinner spinner;
    private Button saveButton;
    private boolean editMode;
    private TextView dateTextView;
    private BottomNavigationView bottomNavigationView;
    private Repo repo = InMemoryRepoImpl.getInstance();
    private NotesListFragment.Controller controller;
    private int currentDay, currentMonth, currentYear;

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
        dateTextView = view.findViewById(R.id.due_date_text_view);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        title = view.findViewById(R.id.title_edit_note);
        description = view.findViewById(R.id.description_edit_note);
        String[] strings = getResources().getStringArray(R.array.importance);
        spinner = view.findViewById(R.id.importance_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.importance, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        if (editMode) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            for (int i = 0; i < strings.length; i++) {
                if (note.getImportance().equals(strings[i])) {
                    spinner.setSelection(i);
                }
            }
            if(note.getDate()!=null){
                dateTextView.setText(note.getDate());
            }
        }
        saveButton = view.findViewById(R.id.button_update_note);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (editMode) {
            note.setTitle(title.getText().toString());
            note.setDescription(description.getText().toString());
            note.setImportance(spinner.getSelectedItem().toString());
            note.setDate(dateTextView.getText().toString());
            repo.update(note);
        } else {
            note = new Note(title.getText().toString(), description.getText().toString(), spinner.getSelectedItem().toString());
            note.setDate(dateTextView.getText().toString());
            repo.create(note);
        }
        controller.saveNote();
    }

    public Note getNote() {
        if (note == null) {
            note = new Note(title.getText().toString(), description.getText().toString());
        }
        return note;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Toast.makeText(getContext(), R.string.favorites, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_change_date:
                callDatePicker();
                return true;
            default:
                return false;
        }
    }


    private void callDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        DatePickerDialog dpDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "." + (month + 1) + "." + year;
                dateTextView.setText(date);
            }
        }, currentYear,  currentMonth, currentDay);
        dpDialog.show();
    }
}
