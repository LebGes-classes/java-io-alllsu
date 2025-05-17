package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassJournal journal = new ClassJournal();
        String filepath = "C:\\Users\\user\\Documents\\Homework\\Journal\\Journal.xlsx";

        //Чтение данных из Excel
        journal.loadFromExcel(filepath);

        //Запуск меню
        Console console = new Console(journal);
        console.showMenu();

        //Сохранение в JSON
        journal.saveToJson("Journal.json");

        //Загрузка из JSON
        ClassJournal newJournal = new ClassJournal();
        newJournal.loadFromJson("Journal.json");

        //Сохранение в Excel
        newJournal.saveToExcel(filepath);
    }
}