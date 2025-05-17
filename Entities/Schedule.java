package org.example;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int groupId;
    private int subjectId;
    private int teacherId;
    private String time;
    private String day;

    public Schedule(int groupId, int subjectId, int teacherId, String time, String day) {
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.time = time;
        this.day = day;
    }
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
