package ua.edu.sumdu.j2se.bekker.tasks.view.impl;

import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.MenuView;

public class MenuViewImpl implements MenuView {

    /**
     * Prints a main menu into CLI.
     */
    @Override
    public void showMenu() {
        ConsolePrinting.println("\n============ Task Manager ===========");
        ConsolePrinting.println("1. Task list");
        ConsolePrinting.println("2. Add Task");
        ConsolePrinting.println("3. Edit Task");
        ConsolePrinting.println("4. Delete Task");
        ConsolePrinting.println("5. Show Calendar");
        ConsolePrinting.println("0. Exit");
        ConsolePrinting.println("=====================================");
        ConsolePrinting.print("Please enter your choice: ");
    }

    /**
     * Prints a menu to editing task into CLI.
     */
    @Override
    public void showEditMenu() {
        ConsolePrinting.println("\n============= Task editing menu =============");
        ConsolePrinting.println("1. Edit title");
        ConsolePrinting.println("2. Edit activity status");
        ConsolePrinting.println("3. Edit time or make task non-repeating)");
        ConsolePrinting.println("4. Edit time/interval or make task repeating)");
        ConsolePrinting.println("0. Return to main menu");
        ConsolePrinting.println("=============================================");
        ConsolePrinting.print("Please enter your choice: ");
    }

}
