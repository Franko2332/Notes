package ru.gb.notes.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {
    private Note note;
    private TextView title;
    private TextView description;
    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        itemView.setOnClickListener(x->{listener.onNoteClickListener(note);});
    }

    void bind(Note note){
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }
}
