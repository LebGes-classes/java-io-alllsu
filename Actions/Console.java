package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Console {
    private ClassJournal journal;
    private Scanner scanner;

    public Console(ClassJournal journal) {
        this.journal = journal;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean launch = true;
        while (launch) {
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("=== Добро пожаловать в классный журнал! ===");
            System.out.println("Главное меню: ");
            System.out.println("1. Посмотреть студентов факультета");
            System.out.println("2. Поставить оценку студенту");
            System.out.println("3. Посмотреть оценки студента");
            System.out.println("4. Открыть расписание факультета");
            System.out.println("5. Узнать средний балл студента");
            System.out.println("6. Добавить студента");
            System.out.println("7. Удалить студента");
            System.out.println("8. Посмотреть расписание преподавателя");
            System.out.println("9. Выйти");
            System.out.print("Выберите действие из списка: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewStudentsInGroup();
                    break;
                case 2:
                    addGradeToStudent();
                    break;
                case 3:
                    viewStudentGrades();
                    break;
                case 4:
                    viewGroupSchedule();
                    break;
                case 5:
                    viewAverageGradeByStudent();
                    break;
                case 6:
                    addStudent();
                    break;
                case 7:
                    removeStudent();
                    break;
                case 8:
                    viewTeacherSchedule();
                    break;
                case 9:
                    System.out.println("Выходим из журнала . . .");
                    launch = false;
            }
        }
    }
    public void viewStudentsInGroup() {
        journal.viewAllGroups();
        System.out.println("Введите ID факультета: ");
        int groupId = scanner.nextInt();
        List<Student> students = journal.getStudentsByGroup(groupId);
        if (students.isEmpty()) {
            System.out.println("В факультете ID = " + groupId + " нет студентов");
        } else {
            System.out.println("Студенты факультета ID = " + groupId + ":");
            for (Student student : students) {
                System.out.println("ID = " + student.getId() + ", имя: " + student.getName());
            }
        }
    }

    public void addGradeToStudent() {
        journal.viewAllGroups();
        System.out.println("Введите ID факультета студента:");
        int groupId = scanner.nextInt();
        journal.viewStudentsByGroup(groupId);
        System.out.println("Введите ID студента: ");
        int studentId = scanner.nextInt();
        if (!journal.getStudents().containsKey(studentId)) {
            System.out.println("Студент с ID = " + studentId + " не найден");
            return;
        }

        journal.viewSubjectsByGroup(groupId);
        System.out.println("Введите ID предмета: ");
        int subjectId = scanner.nextInt();
        if(!journal.getSubjects().containsKey(subjectId)) {
            System.out.println("Предмет с ID = " + subjectId + " не найден");
            return;
        }

        System.out.println("Введите оценку: ");
        int value = scanner.nextInt();
        if (value <= 1 || value > 5) {
            System.out.println("Введите оценку от 2 до 5");
            return;
        }

        journal.addGrade(studentId, subjectId, value);
        System.out.println("Студенту ID = " + studentId + " поставлена оценка " + value);
    }

    public void viewStudentGrades() {
        journal.viewAllGroups();
        System.out.println("Выберите ID факультета: ");
        int groupId = scanner.nextInt();
        journal.getStudentsByGroup(groupId);
        System.out.println("Введите ID студента: ");
        int studentId = scanner.nextInt();
        List<Grade> grades = journal.getStudentGrades(studentId);
        if (grades.isEmpty()) {
            System.out.println("У студента ID = " + studentId + " нет оценок");
        }
        else {
            System.out.println("Оценки студента ID = " + studentId + ":");
            for (Grade grade : grades) {
                System.out.println("ID предмета: " + grade.getStudentId() + ", оценка: " + grade.getValue() + ", дата: " + grade.getDate());
            }
        }
    }
    private void viewGroupSchedule() {
        journal.viewAllGroups();
        System.out.print("Введите ID факультета: ");
        int groupId = scanner.nextInt();
        List<Schedule> schedules = journal.getGroupSchedule(groupId);
        if (schedules.isEmpty()) {
            System.out.println("Для факультета ID = " + groupId + " нет расписания");
        } else {
            System.out.println("Расписание для факультета ID = " + groupId + ":");
            for (Schedule schedule : schedules) {
                System.out.println("ID предмета: " + schedule.getSubjectId() + ", день: " + schedule.getDay() + ", время: " + schedule.getTime());
            }
        }
    }

    private void viewAverageGradeByStudent() {
        journal.viewAllStudents();
        System.out.print("Введите ID студента: ");
        int studentId = scanner.nextInt();
        if (!journal.getStudents().containsKey(studentId)) {
            System.out.println("Студент с ID = " + studentId + " не найден");
            return;
        }
        double average = journal.getAverageGradeByStudent(studentId);
        if (average == 0.0) {
            System.out.println("У студета ID = " + studentId + " нет оценок");
        } else {
            System.out.printf("Средний балл студента ID = " + studentId + ": " + average);
        }
    }

    private void addStudent() {
        System.out.println("Введите имя студента: ");
        String name = scanner.nextLine();
        journal.viewAllGroups();
        System.out.println("Введите ID факультета: ");
        int groupId = scanner.nextInt();
        if (!journal.getGroups().containsKey(groupId)) {
            System.out.println("Факультет ID = " + groupId + " не найден");
            return;
        }
        int nextId = 1; //Устанавливаем ID студента (+1 к последнему)
        if (!journal.getStudents().isEmpty()) {
            nextId = Collections.max(journal.getStudents().keySet());
        }
        Student newStudent = new Student(nextId, name, groupId);
        journal.addStudent(newStudent);
    }

    private void removeStudent() {
        journal.viewAllStudents();
        System.out.print("Введите ID студента: ");
        int studentId = scanner.nextInt();
        if (!journal.getStudents().containsKey(studentId)) {
            System.out.println("Студент ID =  " + studentId + " не найден");
            return;
        }
        journal.removeStudent(studentId);
    }

    private void viewTeacherSchedule() {
        journal.viewAllTeachers();
        System.out.print("Введите ID преподавателя: ");
        int teacherId = scanner.nextInt();
        if (!journal.getTeachers().containsKey(teacherId)) {
            System.out.println("Преподаватель ID = " + teacherId + " не найден");
            return;
        }
        List<Schedule> schedules = journal.getTeacherSchedule(teacherId);
        if (schedules.isEmpty()) {
            System.out.println("Нет расписания у преподавателя ID = " + teacherId);
        } else {
            System.out.println("Расписание преподавателя: ");
            for (Schedule schedule : schedules) {
                System.out.println("ID предмета: " + schedule.getSubjectId() + ", день: " + schedule.getDay() + ", время: " + schedule.getTime());
            }
        }
    }
}