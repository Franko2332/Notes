package ru.gb.notes.recycler;


import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notes.R;
import ru.gb.notes.data.Note;
import ru.gb.notes.interfaces.PopupMenuItemClickListener;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
    private Note note;
    private TextView title;
    private TextView description;
    private TextView importance;
    private ImageView imageView;
    private PopupMenu popupMenu;
    private TextView date;
    private PopupMenuItemClickListener popupMenuItemClickListener;

    public NoteHolder(View itemView, NotesAdapter.OnNoteClickListener listener,
                      PopupMenuItemClickListener popupMenuItemClickListener) {
        super(itemView);
        this.popupMenuItemClickListener = popupMenuItemClickListener;
        imageView = itemView.findViewById(R.id.context_menu);
        importance = itemView.findViewById(R.id.note_importance);
        date = itemView.findViewById(R.id.note_date_text_view);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        itemView.setOnClickListener(x -> {
            listener.onNoteClickListener(note);
        });
        popupMenu = new PopupMenu(itemView.getContext(), itemView);
        popupMenu.inflate(R.menu.context);
        imageView.setOnClickListener(p -> popupMenu.show());
        popupMenu.setOnMenuItemClickListener(this);
    }

    void bind(Note note) {
        this.note = note;
        date.setText(note.getDate());
        importance.setText(note.getImportance());
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_delete:
                popupMenuItemClickListener.click(R.id.context_delete, note, getAdapterPosition());
                return true;
            case R.id.context_modify:
                popupMenuItemClickListener.click(R.id.context_modify, note, getAdapterPosition());
                return true;
        }
        return false;
    }
}
