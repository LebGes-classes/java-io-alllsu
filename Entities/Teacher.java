package org.example;

import java.util.List;

public class Teacher extends Entity{
    private List<Group> groupList;
    private List<Subject> subjectList;
    private List<String > scheduleId;

    public Teacher(int id, String name) {
        super(id, name);
        this.groupList = groupList;
        this.subjectList = subjectList;
        this.scheduleId = scheduleId;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<String> getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(List<String> scheduleId) {
        this.scheduleId = scheduleId;
    }
}

