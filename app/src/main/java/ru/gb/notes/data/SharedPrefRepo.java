package ru.gb.notes.data;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;


public class SharedPrefRepo implements Repo {
    private static SharedPrefRepo sharedPrefRepo;
    private SharedPreferences sharedPreferences;
    private ArrayList<Note> notes = new ArrayList<>();


    public static SharedPrefRepo getInstance(Context context) {
        if (sharedPrefRepo == null) {
            sharedPrefRepo = new SharedPrefRepo(context);
        }
        return sharedPrefRepo;
    }

    private SharedPrefRepo(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.NOTES_IN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.notes = getAll();
    }

    @Override
    public int create(Note note) {
        int id = getLastId();
        note.setId(id);
        Gson gson = new Gson();
        notes.add(note);
        String result = gson.toJson(note);
        sharedPreferences.edit().putString(String.valueOf(id), result).apply();
        return id;
    }

    private int getLastId() {
        return sharedPreferences.getAll().size();
    }

    @Override
    public Note read(int id) {
        Note note;
        if (sharedPreferences.contains(String.valueOf(id))) {
            note = getAll().get(id);
            return note;
        }
        return null;
    }

    @Override
    public void update(Note note) {
        if (sharedPreferences.contains(String.valueOf(note.getId()))) {
            sharedPreferences.edit().putString(String.valueOf(note.getId()), new Gson().toJson(note)).apply();
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getId() == note.getId()) {
                    notes.set(i, note);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        if (sharedPreferences.contains(String.valueOf(id))) {
            sharedPreferences.edit().remove(String.valueOf(id)).apply();
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getId() == id) {
                    notes.remove(i);
                }
            }
        }
    }

    @Override
    public ArrayList<Note> getAll() {
        Map<String, ?> notesMap = sharedPreferences.getAll();
        Note note;
        notes.clear();
        for (Map.Entry<String, ?> entry : notesMap.entrySet()) {
            note = new Gson().fromJson(entry.getValue().toString(),
                    new TypeToken<Note>() {
                    }.getType());
            notes.add(note);
        }
        return notes;
    }

}
