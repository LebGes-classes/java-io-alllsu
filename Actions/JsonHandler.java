package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {
    private final ObjectMapper objectMapper;

    public JsonHandler() {
        this.objectMapper = new ObjectMapper();
    }

    public void saveToJson(ClassJournal journal, String filePath) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("Students", journal.getStudents());
        data.put("Teachers", journal.getTeachers());
        data.put("Subjects", journal.getSubjects());
        data.put("Grades", journal.getGrades());
        data.put("Groups", journal.getGroups());
        data.put("Schedules", journal.getSchedules());

        objectMapper.writeValue(new File(filePath), data);
    }

    @SuppressWarnings("unchecked")
    public void loadFromJson(ClassJournal journal, String filePath) throws IOException {
        Map<String, Object> data = objectMapper.readValue(new File(filePath), Map.class);

        journal.getStudents().clear();
        journal.getStudents().putAll((Map<Integer, Student>) data.get("Students"));
        journal.getTeachers().clear();
        journal.getTeachers().putAll((Map<Integer, Teacher>) data.get("Teachers"));
        journal.getSubjects().clear();
        journal.getSubjects().putAll((Map<Integer, Subject>) data.get("Subjects"));
        journal.getGrades().clear();
        journal.getGrades().putAll((Map<String, Grade>) data.get("Grades"));
        journal.getGroups().clear();
        journal.getGroups().putAll((Map<Integer, Group>) data.get("Groups"));
        journal.getSchedules().clear();
        journal.getSchedules().addAll((List<Schedule>) data.get("Schedules"));
    }
}
