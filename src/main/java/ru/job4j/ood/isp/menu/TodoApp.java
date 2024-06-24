package ru.job4j.ood.isp.menu;

import java.util.Optional;
import java.util.Scanner;

/**
 * 6. Создайте простенький класс TodoApp. Этот класс будет представлять собой консольное приложение, которое позволяет:
 * Добавить элемент в корень меню;
 * Добавить элемент к родительскому элементу;
 * Вызвать действие, привязанное к пункту меню (действие можно сделать константой,
 * например, ActionDelete DEFAULT_ACTION = () -> System.out.println("Some action") и указывать при добавлении элемента в меню);
 * Вывести меню в консоль.
 */
public class TodoApp {
    private static final String MAIN_MENU = """
            - Выберите действие -
            1. Добавить элемент в корень меню
            2. Добавить элемент к родительскому элементу
            3. Вызвать действие, привязанное к пункту меню
            4. Вывести меню в консоль
            5. Выход из программы""";
    private  static  final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");
    private static boolean active = true;

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        Scanner scanner = new Scanner(System.in);
        while (active) {
            System.out.println(MAIN_MENU);
            String string = scanner.nextLine();
            switch (string) {
                case "1" -> {
                    System.out.println("Введите имя элемента:");
                    string = scanner.nextLine();
                    if (!string.isBlank() && menu.add(null, string, DEFAULT_ACTION)) {
                        System.out.println("Добавлен корневой элемент");
                    }
                }
                case "2" -> {
                    System.out.println("Введите имя родительского элемента:");
                    String rootString = scanner.nextLine();
                    System.out.println("Введите имя элемента:");
                    string = scanner.nextLine();
                    if (!string.isBlank() && menu.add(rootString, string, DEFAULT_ACTION)) {
                        System.out.println("Добавлен элемент к родительскому элементу");
                    }
                }
                case "3" -> {
                    System.out.println("Введите имя элемента:");
                    string = scanner.nextLine();
                    Optional<Menu.MenuItemInfo> menuItemInfo = menu.select(string);
                    if (menuItemInfo.isPresent()) {
                        menuItemInfo.get().getActionDelegate().delegate();
                    } else {
                        System.out.println("Элемент не найден");
                    }
                }
                case "4" -> {
                    System.out.println("- Menu -");
                    new Printer().print(menu);
                }
                case "5" -> {
                    active = false;
                    System.out.println("Выход из программы");
                }
                default -> {

                }
            }
        }
    }
}
