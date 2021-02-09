package ua.edu.sumdu.j2se.bekker.tasks.view;

import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.view.impl.CalendarViewImpl;
import ua.edu.sumdu.j2se.bekker.tasks.view.impl.MenuViewImpl;
import ua.edu.sumdu.j2se.bekker.tasks.view.impl.TaskViewImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainViewDecorator {
     private static final Scanner scanner = new Scanner(System.in);
     private final MenuView menuView;
     private final TaskView taskView;
     private final CalendarView calendarView;

     public MainViewDecorator() {
         this.menuView = new MenuViewImpl();
         this.taskView = new TaskViewImpl(scanner);
         this.calendarView = new CalendarViewImpl(scanner);
     }

    public void showMenu() {
         menuView.showMenu();
    }

    public void showEditMenu() {
        menuView.showEditMenu();
    }

    public void showTaskList(AbstractTaskList model) {
        taskView.showTaskList(model);
    }

    public int selectTask(AbstractTaskList list) {
         return taskView.selectTask(list);
    }

    public int removeTask(AbstractTaskList model) {
         return taskView.removeTask(model);
    }

    public void getCalendar(AbstractTaskList list) throws ClassNotFoundException {
         calendarView.getCalendar(list);
    }

    public String getTitle() {
         return taskView.getTitle();
    }

    public boolean getActiveStatus(){
         return taskView.getActiveStatus();
    }

    public TaskStatus getTaskStatus(){
        return taskView.getIsTaskRepeated();
    }

    public int getInterval() {
        return taskView.getInterval();
    }

    public LocalDateTime parseDateTime() {
        return calendarView.parseDateTime();
    }

    public void displayTaskInfo(Task task) {
        taskView.displayTaskInfo(task);
    }

    public boolean checkUserAnswer() {
        return taskView.checkUserAnswer();
    }

}
