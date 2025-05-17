package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    public void loadToJournal(ClassJournal journal, String filePath) throws IOException {
        Map<Integer, Student> students = journal.getStudents();
        Map<Integer, Teacher> teachers = journal.getTeachers();
        Map<Integer, Group> groups = journal.getGroups();
        Map<Integer, Subject> subjects = journal.getSubjects();
        List<Schedule> schedules = journal.getSchedules();
        Map<String, Grade> grades = journal.getGrades();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet groupSheet = workbook.getSheet("Groups");
            for (Row row : groupSheet) {
                if (row.getRowNum() == 0) continue;
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                int quantity = (int) row.getCell(2).getNumericCellValue();
                groups.put(id, new Group(id, name, quantity));
            }

            Sheet teacherSheet = workbook.getSheet("Teachers");
            for (Row row : teacherSheet) {
                if (row.getRowNum() == 0) continue;
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                String groupIds = row.getCell(2).getStringCellValue();
                int subjectId = (int) row.getCell(3).getNumericCellValue();
                List<Group> groupList = new ArrayList<>();
                for (String groupId : groupIds.split(", ")) {
                    Group group = groups.get(Integer.parseInt(groupId));
                    if (group != null) {
                        groupList.add(group);
                    }
                }
                teachers.put(id, new Teacher(id, name));
            }

            Sheet studentSheet = workbook.getSheet("Students");
            for (Row row : studentSheet) {
                if (row.getRowNum() == 0) continue;
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                int groupId = (int) row.getCell(2).getNumericCellValue();
                Group group = groups.get(groupId);
                if (group != null) {
                    students.put(id, new Student(id, name, groupId));
                    group.setQuantity(group.getQuantity() + 1);
                }
            }

            Sheet scheduleSheet = workbook.getSheet("Schedules");
            for (Row row : scheduleSheet) {
                if (row.getRowNum() == 0) continue;
                int groupId = (int) row.getCell(0).getNumericCellValue();
                int subjectId = (int) row.getCell(1).getNumericCellValue();
                int teacherId = (int) row.getCell(2).getNumericCellValue();
                String time = row.getCell(3).getStringCellValue();
                String day = row.getCell(4).getStringCellValue();
                schedules.add(new Schedule(groupId, subjectId, teacherId, time, day));
            }
        }
    }
    public void saveFromJournal(ClassJournal journal, String filePath) throws IOException {
        Map<Integer, Student> students = journal.getStudents();
        Map<Integer, Teacher> teachers = journal.getTeachers();
        Map<Integer, Subject> subjects = journal.getSubjects();
        Map<String, Grade> grades = journal.getGrades();
        Map<Integer, Group> groups = journal.getGroups();
        List<Schedule> schedules = journal.getSchedules();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet groupSheet = workbook.createSheet("Groups");
            Row groupHeader = groupSheet.createRow(0);
            groupHeader.createCell(0).setCellValue("ID");
            groupHeader.createCell(1).setCellValue("Name");
            groupHeader.createCell(2).setCellValue("Quantity");
            int groupRowNum = 1;
            for (Group group : groups.values()) {
                Row row = groupSheet.createRow(groupRowNum++);
                row.createCell(0).setCellValue(group.getId());
                row.createCell(1).setCellValue(group.getName());
                row.createCell(2).setCellValue(group.getQuantity());
            }

            Sheet subjectSheet = workbook.createSheet("Subjects");
            Row subjectHeader = subjectSheet.createRow(0);
            subjectHeader.createCell(0).setCellValue("ID");
            subjectHeader.createCell(1).setCellValue("Name");
            int subjectRowNum = 1;
            for (Subject subject : subjects.values()) {
                Row row = subjectSheet.createRow(subjectRowNum++);
                row.createCell(0).setCellValue(subject.getId());
                row.createCell(1).setCellValue(subject.getName());
            }

            Sheet teacherSheet = workbook.createSheet("Teachers");
            Row teacherHeader = teacherSheet.createRow(0);
            teacherHeader.createCell(0).setCellValue("ID");
            teacherHeader.createCell(1).setCellValue("Name");
            teacherHeader.createCell(2).setCellValue("Group ID");
            teacherHeader.createCell(3).setCellValue("Subject ID");
            int teacherRowNum = 1;
            for (Teacher teacher : teachers.values()) {
                Row row = teacherSheet.createRow(teacherRowNum++);
                row.createCell(0).setCellValue(teacher.getId());
                row.createCell(1).setCellValue(teacher.getName());
                String groupIds = "";
                for (Group group : teacher.getGroupList()) {
                    groupIds += group.getId() + ",";
                }
                if (!groupIds.isEmpty() && groupIds.endsWith(",")) {
                    groupIds = groupIds.substring(0, groupIds.length() - 1);
                }
                row.createCell(2).setCellValue(groupIds);
                String subjectIds = "";
                for (Subject subject : teacher.getSubjectList()) {
                    subjectIds += subject.getId() + ",";
                }
                if (!subjectIds.isEmpty() && subjectIds.endsWith(",")) {
                    subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
                }
                row.createCell(3).setCellValue(subjectIds);
            }

            Sheet studentSheet = workbook.createSheet("Students");
            Row studentHeader = studentSheet.createRow(0);
            studentHeader.createCell(0).setCellValue("ID");
            studentHeader.createCell(1).setCellValue("Name");
            studentHeader.createCell(2).setCellValue("Group ID");
            int studentRowNum = 1;
            for (Student student : students.values()) {
                Row row = studentSheet.createRow(studentRowNum++);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getGroupId());
            }

            Sheet scheduleSheet = workbook.createSheet("Schedules");
            Row scheduleHeader = scheduleSheet.createRow(0);
            scheduleHeader.createCell(0).setCellValue("Group ID");
            scheduleHeader.createCell(1).setCellValue("Subject ID");
            scheduleHeader.createCell(2).setCellValue("Teacher ID");
            scheduleHeader.createCell(3).setCellValue("Time");
            scheduleHeader.createCell(4).setCellValue("Day");
            int scheduleRowNum = 1;
            for (Schedule schedule : schedules) {
                Row row = scheduleSheet.createRow(scheduleRowNum++);
                row.createCell(0).setCellValue(schedule.getGroupId());
                row.createCell(1).setCellValue(schedule.getSubjectId());
                row.createCell(2).setCellValue(schedule.getTeacherId());
                row.createCell(3).setCellValue(schedule.getTime());
                row.createCell(4).setCellValue(schedule.getDay());
            }

            Sheet gradeSheet = workbook.createSheet("Grades");
            Row gradeHeader = gradeSheet.createRow(0);
            gradeHeader.createCell(0).setCellValue("ID");
            gradeHeader.createCell(1).setCellValue("Student ID");
            gradeHeader.createCell(2).setCellValue("Subject ID");
            gradeHeader.createCell(3).setCellValue("Value");
            gradeHeader.createCell(4).setCellValue("Date");
            int gradeRowNum = 1;
            for (Grade grade : grades.values()) {
                Row row = gradeSheet.createRow(gradeRowNum++);
                row.createCell(0).setCellValue(grade.getId());
                row.createCell(1).setCellValue(grade.getStudentId());
                row.createCell(2).setCellValue(grade.getSubjectId());
                row.createCell(3).setCellValue(grade.getValue());
                row.createCell(4).setCellValue(grade.getDate().toString());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }
}
