package ru.gb.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.gb.notes.R;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;
import ru.gb.notes.recycler.NotesAdapter;

public class NotesListFragment extends Fragment implements NotesAdapter.OnNoteClickListener {
    Repo repo = InMemoryRepoImpl.getInstance();
    RecyclerView recycler;
    NotesAdapter adapter;
    Controller controller;
    FloatingActionButton fab;

    interface Controller {
        void addNote();

        void editNote(Note note);

        void saveNote();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("onViewCreated", "hello");
        init(view);
    }

    private void init(View view) {
        recycler = view.findViewById(R.id.list);
        fab = view.findViewById(R.id.fab);
        controller = (Controller) getActivity();
        adapter = new NotesAdapter();
        adapter.setNotes(repo.getAll());
        adapter.setOnNoteClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        fab.setOnClickListener(x -> {
            controller.addNote();
        });
    }


    @Override
    public void onNoteClickListener(Note note) {
        controller.editNote(note);
    }
}
