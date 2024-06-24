package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        menu.forEach(menuItemInfo -> System.out.printf("%s%s %s%s",
                "----".repeat(menuItemInfo.getNumber().split("\\.", -1).length - 2),
                menuItemInfo.getNumber(),
                menuItemInfo.getName(),
                System.lineSeparator()));
    }
}
