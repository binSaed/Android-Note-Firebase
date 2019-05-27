
/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */
package com.example.abdonote.Model;

import java.io.Serializable;

public class ClassDate implements Serializable {
    private String id;
    private String time;
    private String title;
    private String note;

    public ClassDate() {
    }

    public ClassDate(String id, String time, String title, String note) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
