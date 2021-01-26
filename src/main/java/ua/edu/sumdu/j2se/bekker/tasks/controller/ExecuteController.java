package ua.edu.sumdu.j2se.bekker.tasks.controller;


import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.MainViewDecorator;

import java.util.Scanner;

public class ExecuteController {
    private final MainViewDecorator view;
    private final TaskController taskController;

    public ExecuteController(MainViewDecorator commandView, TaskController taskController) {
        this.view = commandView;
        this.taskController = taskController;
    }
    public void execute(Scanner scanner) {
        while (true) {
            view.showMenu();
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                continue;
            }
            if (choice == 0) {
                ConsolePrinting.print("Do you want to exit? (Yes/No): ");
                if (view.checkUserAnswer()) {
                    ConsolePrinting.println("Program has been completed!");
                    break;
                }
            } else {
                switch (choice) {
                    case 1:
                        taskController.getList();
                        break;
                    case 2:
                        ConsolePrinting.print("Are you sure you want to add a task? (Yes/No): ");
                        if ((view.checkUserAnswer())) taskController.addTask();
                        break;
                    case 3:
                        ConsolePrinting.print("Are you sure you want to edit a task? (Yes/No): ");
                        if ((view.checkUserAnswer())) taskController.editTask();
                        break;
                    case 4:
                        ConsolePrinting.print("Are you sure you want to delete a task? (Yes/No): ");
                        if ((view.checkUserAnswer())) taskController.removeTask();
                        break;
                    case 5:
                        taskController.getCalendar();
                        break;
                    default:
                }
            }
        }
    }
}
