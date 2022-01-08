package ru.gb.notes.data;

import java.io.Serializable;

public class Note implements Serializable {
private Integer id;
private String title;
private String description;
private String importance;
private String date;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.importance = Constants.Importance.IMPORTANCE_NOT_SELECTED.toString();

    }

    public Note(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public Note(Integer id, String title, String description, Constants.Importance importance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance.toString();
    }
    public Note (String title, String description, String importance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
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

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getImportance() {
        return importance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
