package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ClassJournal {
    private Map<Integer, Student> students = new HashMap<>();
    private Map<Integer, Teacher> teachers = new HashMap<>();
    private Map<Integer, Group> groups = new HashMap<>();
    private Map<Integer, Subject> subjects = new HashMap<>();
    private List<Schedule> schedules = new ArrayList<>();
    private Map<String, Grade> grades = new HashMap<>();
    private final Excel excel = new Excel();
    private final JsonHandler jsonHandler = new JsonHandler();

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public Map<Integer, Teacher> getTeachers() {
        return teachers;
    }

    public Map<Integer, Group> getGroups() {
        return groups;
    }

    public Map<Integer, Subject> getSubjects() {
        return subjects;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public Map<String, Grade> getGrades() {
        return grades;
    }

    public void loadFromExcel(String filepath) throws IOException {
        excel.loadToJournal(this, filepath);
    }

    public void saveToExcel(String filepath) throws IOException {
        excel.saveFromJournal(this, filepath);
    }

    public void saveToJson(String filepath) throws IOException {
        jsonHandler.saveToJson(this, filepath);
    }

    public void loadFromJson(String filepath) throws IOException {
        jsonHandler.loadFromJson(this, filepath);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        if (groups.containsKey(student.getGroupId())) {
            groups.get(student.getGroupId()).setQuantity(groups.get(student.getGroupId()).getQuantity() + 1);
        }
    }

    public void removeStudent(int studentId) {
        Student student = students.get(studentId);
        //Удаляем все оценки ученика
        Iterator<Map.Entry<String, Grade>> iterator = grades.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Grade> entry = iterator.next();
            if (entry.getValue().getStudentId() == studentId) {
                iterator.remove();
            }
        }
        //Уменьшаем количество учеников в группе
        int groupId = student.getGroupId();
        if (groups.containsKey(groupId)) {
            Group group = groups.get(groupId);
            group.setQuantity(group.getQuantity() - 1);
        }
        students.remove(studentId);
    }

    public void addGrade(int studentId, int subjectId, int value) {
        String gradeId = UUID.randomUUID().toString(); //Генерируем ID оценки
        Grade grade = new Grade(gradeId, studentId, subjectId, value, LocalDate.now());
        grades.put(gradeId, grade);
        Student student = students.get(studentId);
        if (student != null) {
            student.addGrade(grade);
        }
    }

    public List<Grade> getStudentGrades(int studentId) {
        Student student = students.get(studentId);
        if (student == null) {
            return new ArrayList<>();
        }
        return student.getGrades();
    }

    public double getAverageGradeByStudent(int studentId) {
        List<Grade> grades = getStudentGrades(studentId);
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getValue();
        }
        return (double) sum/grades.size();
    }

    public List<Student> getStudentsByGroup(int groupId) {
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getGroupId() == groupId) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Schedule> getGroupSchedule(int groupId) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getGroupId() == groupId) {
                result.add(schedule);
            }
        }
        return result;
    }

    public List<Subject> getSubjectsByGroup(int groupId) {
        List<Subject> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getGroupId() == groupId) {
                Subject subject = subjects.get(schedule.getSubjectId());
                if (subject != null && !result.contains(subject)) {
                    result.add(subject);
                }
            }
        }
        return result;
    }

    public void viewAllGroups() {
        Map<Integer, Group> groups = getGroups();
        if (groups.isEmpty()) {
            System.out.println("Список факультетов пуст");
        } else {
            System.out.println("Список всех факультетов:");
            for (Group group : groups.values()) {
                System.out.println("ID = " + group.getId() + ", название: " + group.getName());
            }
        }
    }

    public void viewAllStudents() {
        Map<Integer, Student> students = getStudents();
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст");
        } else {
            System.out.println("Список всех студентов:");
            for (Student student : students.values()) {
                System.out.println("ID = " + student.getId() + ", имя: " + student.getName());
            }
        }
    }

    public void viewAllTeachers() {
        Map<Integer, Teacher> teachers = getTeachers();
        if (teachers.isEmpty()) {
            System.out.println("Список преподавателей пуст");
        } else {
            System.out.println("Список всех преподавателей:");
            for (Teacher teacher : teachers.values()) {
                System.out.println("ID = " + teacher.getId() + ", имя: " + teacher.getName());
            }
        }
    }

    public void viewStudentsByGroup(int groupId) {
        Map<Integer, Student> students = getStudents();
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст");
        } else {
            System.out.println("Список всех студентов факультета ID = " + groupId);
            for (Student student : students.values()) {
                if(student.getGroupId() == groupId){
                    System.out.println("ID = " + student.getId() + ", имя: " + student.getName());
                }
            }
        }
    }

    public void viewSubjectsByGroup(int groupId) {
        List<Subject> subjects = getSubjectsByGroup(groupId);
        if (subjects.isEmpty()) {
            System.out.println("Список предметов факультета пуст");
        } else {
            System.out.println("Предметы факультета " + getGroups().get(groupId).getName() + ":");
            for (Subject subject : subjects) {
                System.out.println("ID = " + subject.getId() + ", название: " + subject.getName());
            }
        }
    }

    public List<Schedule> getTeacherSchedule(int teacherId) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getTeacherId() == teacherId) {
                result.add(schedule);
            }
        }
        return result;
    }
}