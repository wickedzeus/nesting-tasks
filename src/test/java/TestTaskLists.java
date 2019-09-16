import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTaskLists {

    @BeforeEach
    void testPrep() {
        TaskList.clearTaskList();
    }

    @Test
    void testTaskCreation() {
        String taskName = "Test Task";
        Task testTask = new Task(taskName, "Testing task creation.");
        TaskList.addTask(testTask);

        assertEquals(TaskList.getTaskCount(), 1);
        assertEquals(TaskList.getTask(taskName).getName(), taskName);
    }

    @Test
    void testTaskDeletion() {
        String taskName = "Test Task";
        Task testTask = new Task(taskName, "Testing task creation.");
        TaskList.addTask(testTask);

        TaskList.deleteTask(taskName);

        assertEquals(TaskList.getTaskCount(), 0);
    }

    @Test
    void testChildCreation() {

    }
}
