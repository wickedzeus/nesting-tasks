import java.util.HashMap;
import java.util.Map;

public class TaskList {
    private static Map<String, Task> taskMap = new HashMap<String, Task>();
//    private static TaskList instance;

//    private TaskList() {
//        taskMap = new HashMap<String, Task>();
//    }
//
//    private static TaskList getInstance() {
//        if(instance == null) {
//            instance = new TaskList();
//        }
//
//        return instance;
//    }

    public static void addTask(Task task) {
        taskMap.put(task.getName(),task);
    }

    public static void updateTask(Task task) {
        addTask(task);
    }

    public static Task getTask(String task) {
        return taskMap.get(task);
    }

    public static int getTaskCount() {
        return taskMap.size();
    }

    public static void deleteTask(String name) {
        Task task = taskMap.get(name);
        if(task.hasChildren()) {
            for(String child : task.getChildren()) {
                deleteTask(child);
            }
        }
        if(task.hasParent()) {
            Task parent = taskMap.get(task.getParentName());
            parent.removeChild(name);
        }
        taskMap.remove(name);
    }

    public static void clearTaskList() {
        taskMap = new HashMap<String, Task>();
    }
}
