package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenPrintThenReturnSame(@TempDir Path fileDir) throws IOException {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        PrintStream stdOut = System.out;
        File file = fileDir.resolve("file.txt").toFile();
        try (PrintStream stream = new PrintStream(file)) {
            System.setOut(stream);
            new Printer().print(menu);
        }
        System.setOut(stdOut);
        StringBuilder builder = new StringBuilder();
        try (FileReader reader = new FileReader(file)) {
            int i = reader.read();
            while (i != -1) {
                builder.append((char) i);
                i = reader.read();
            }
        }
        String expected = "1. Сходить в магазин" + System.lineSeparator()
                + "----1.1. Купить продукты" + System.lineSeparator()
                + "--------1.1.1. Купить хлеб" + System.lineSeparator()
                + "--------1.1.2. Купить молоко" + System.lineSeparator()
                + "2. Покормить собаку" + System.lineSeparator();
        assertThat(builder.toString()).isEqualTo(expected);
    }
}