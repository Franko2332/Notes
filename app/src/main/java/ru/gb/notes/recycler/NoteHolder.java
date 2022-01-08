package ru.gb.notes.recycler;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {
    private Note note;
    private TextView title;
    private TextView description;
    private TextView importance;

    public NoteHolder(View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        importance = itemView.findViewById(R.id.note_importance);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        itemView.setOnClickListener(x->{listener.onNoteClickListener(note);});
    }

    void bind(Note note){
        this.note = note;
        importance.setText(note.getImportance());
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }
}
