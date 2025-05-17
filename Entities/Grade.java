package org.example;

import java.io.Serializable;
import java.time.LocalDate;

public class Grade implements Serializable {
    private String id;
    private int studentId;
    private int subjectId;
    private int value;
    private LocalDate date;

    public Grade(String id, int studentId, int subjectId, int value, LocalDate date) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.value = value;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
