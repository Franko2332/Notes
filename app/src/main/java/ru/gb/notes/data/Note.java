package ru.gb.notes.data;

import java.io.Serializable;

public class Note implements Serializable {
private Integer id;
private String title;
private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
