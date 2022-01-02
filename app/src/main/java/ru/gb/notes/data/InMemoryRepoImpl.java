package ru.gb.notes.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImpl implements Repo {
    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;
    private static InMemoryRepoImpl repo;

    public static Repo getInstance() {
        if(repo==null){
            repo = new InMemoryRepoImpl();
        }
        return repo;
    }

    private InMemoryRepoImpl() {

    }

    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public Note read(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                return notes.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(i);
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }
}
