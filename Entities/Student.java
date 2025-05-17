package org.example;

import java.util.ArrayList;
import java.util.List;

public class Student extends Entity{
    private int groupId;
    private List<Grade> grades = new ArrayList<>();

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Student(int id, String name, int groupId) {
        super(id, name);
        this.groupId = groupId;
        this.grades = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
