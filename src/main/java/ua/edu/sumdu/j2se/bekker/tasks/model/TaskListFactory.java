package ua.edu.sumdu.j2se.bekker.tasks.model;

public class TaskListFactory {

    /**
     * This is factory method it produces objects of two types
     * one is ARRAY - ArrayTaskList
     * second one is LINKED - LinkedTaskList
     *
     * @param types enum that contains two constants ARRAY and LINKED
     *              depending on this parameter corresponding object of class will be created
     * @return AbstractTaskList as we have derived classes from AbstractTaskList user will get
     *              one of two classes or exception in case the given type is does not exist
     */
    public static AbstractTaskList createTaskList(ListTypes.types types) throws ClassNotFoundException {
        switch (types) {
            case ARRAY: return new ArrayTaskList();
            case LINKED: return new LinkedTaskList();
            default: throw new ClassNotFoundException("Given class type is not supported");
        }
    }

}
