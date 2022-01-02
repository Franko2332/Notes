package ru.gb.notes.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClickListener(Note note);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        noteHolder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
