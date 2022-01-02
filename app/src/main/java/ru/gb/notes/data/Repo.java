package ru.gb.notes.data;

import java.util.List;

public interface Repo {
    //CRUD
    //-----
    //CREATE
    //READ
    //UPDATE
    //DELETE

    int create(Note note);

    Note read(int id);

    void update(Note note);

    void delete(int id);

    List<Note> getAll();
}
