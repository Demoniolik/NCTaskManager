package ua.edu.sumdu.j2se.bekker.tasks.controller;

import ua.edu.sumdu.j2se.bekker.tasks.controller.notification.NotificationManager;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.view.MainViewDecorator;

import java.util.Scanner;

/**
 * Responses for the relationship between model and view.
 * Starts Notification manager in daemon thread.
 */
public class MainControllerDecorator {
    private final Scanner scanner;
    private final ExecuteController executeController;

    /**
     * @param list model (task list).
     * @param view representation.
     */
    public MainControllerDecorator(AbstractTaskList list, MainViewDecorator view) {
        scanner = new Scanner(System.in);
        TaskController taskController = new TaskController(view, list);
        executeController = new ExecuteController(view, taskController);
        NotificationManager notifications = new NotificationManager(list);
        notifications.setDaemon(true);
        notifications.start();
    }

    /**
     * Method starts task manager working.
     */
    public void execute() {
        executeController.execute(scanner);
    }

}
