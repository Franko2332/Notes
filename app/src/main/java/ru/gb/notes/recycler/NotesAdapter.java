package ru.gb.notes.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;
import ru.gb.notes.interfaces.PopupMenuItemClickListener;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener listener;
    private PopupMenuItemClickListener popupMenuItemClickListener;

    public interface OnNoteClickListener {
        void onNoteClickListener(Note note);
    }

    public void setPopupMenuItemClickListener(PopupMenuItemClickListener popupMenuItemClickListener){
        this.popupMenuItemClickListener = popupMenuItemClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(view, listener, popupMenuItemClickListener);
    }

    @Override
    public void onBindViewHolder(NoteHolder noteHolder, int position) {
        noteHolder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
