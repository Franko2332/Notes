package ru.gb.notes.interfaces;

import ru.gb.notes.data.Note;

public interface PopupMenuItemClickListener {
    void click(int command, Note note, int position);
}
