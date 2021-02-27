package ua.edu.sumdu.j2se.bekker.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.controller.MainControllerDecorator;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.ListTypes;
import ua.edu.sumdu.j2se.bekker.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.bekker.tasks.model.TaskListFactory;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.MainViewDecorator;

import java.io.IOException;

public class TaskManager {
    public final static String DATA_JSON_PATH = "data.json";
    private static final Logger logger = Logger.getLogger(TaskManager.class);
    private AbstractTaskList list;

    /**
     * Constructs app, inits array task list.
     */
    public TaskManager() {
        try {
            this.list = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    /**
     * Runs task manager. Deserializes task list from data.txt
     * When the application is correctly finished saves (serializes)
     * task list to the data.txt file.
     */
    public void launch() {
        try {
            TaskIO.loadFromFileStorage(list, DATA_JSON_PATH);
        } catch (IOException exception) {
            ConsolePrinting.println("Error. Failed to load data from a file storage");
        }
        MainViewDecorator view = new MainViewDecorator(); // Here you need to remove scanner as parameter
        MainControllerDecorator controller = new MainControllerDecorator(list, view);
        controller.execute();
        try {
            TaskIO.saveToFileStorage(list, DATA_JSON_PATH);
        } catch (IOException exception) {
            ConsolePrinting.println("Error. Failed to save data to file");
        }
        logger.info("The program is finished by user.");
    }
}
