import java.util.ArrayList;
import java.util.List;

public class Task {

    private String parentName;
    private String name;
    private String description;
    private String problemStatement;
    private State state = State.TO_DO;
    private int progressPrcnt = 0;
    private boolean completed = false;
    private List<String> children;
    private List<String> blockers;

    public Task(String name, String problemStatement) {
        this.parentName = "";
        this.name = name;
        this.problemStatement = problemStatement;
        this.description = "";
    }

    public Task(String parentName, String name, String problemStatement) {
        this.parentName = parentName;
        this.name = name;
        this.problemStatement = problemStatement;
        this.description = "";
    }

    public Task(String parentName, String name, String problemStatement, String description) {
        this.parentName = parentName;
        this.name = name;
        this.problemStatement = problemStatement;
        this.description = description;
    }

    public boolean hasParent() {
        return (!parentName.equals(""));
    }
    public String getParentName() {
        return parentName;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getProblemStatement() {
        return problemStatement;
    }
    public String getState() {
        updateProgress();
        return state.toString();
    }
    public int getProgress() {
        updateProgress();
        return progressPrcnt;
    }
    public boolean isCompleted() {
        updateProgress();
        return completed;
    }
    public boolean hasChildren() {
        return (children != null);
    }
    public List<String> getChildren() {
        return children;
    }
    public void addChild(String child) {
        if(children == null) children = new ArrayList<String>();
        children.add(child);
        updateProgress();
    }
    public void removeChild(String child) {
        children.remove(child);
        updateProgress();
    }
    public boolean hasBlockers() {
        return (blockers != null);
    }
    public void addBlocker(String blocker) {
        if(blockers == null) blockers = new ArrayList<String>();
        blockers.add(blocker);
        updateProgress();
    }
    public List<String> getBlockers() {
        return blockers;
    }
    public void clearBlocker(String blocker) {
        blockers.remove(blocker);
        updateProgress();
    }

    public void markAsDone() {
        for(String child :children) {
            Task childTask = TaskList.getTask(child);
            if(!childTask.completed) {childTask.markAsDone();TaskList.updateTask(childTask);}
        }
        updateProgress();
        if(hasParent()) {
            Task parent = TaskList.getTask(parentName);
            parent.updateProgress();
            TaskList.updateTask(parent);
        }
    }

    public boolean updateProgress() {
        boolean updateIssue = false;

        if(name == null && problemStatement == null) updateIssue = true;

        int totalCount = children.size();
        int doneCount = 0;

        if(state == State.IN_PROGRESS && (blockers != null && !blockers.isEmpty())) state = State.BLOCKED;

        for(String child : children) {
            Task childTask = TaskList.getTask(child);
            if((state == State.TO_DO) && (childTask.getState().equals("IN_PROGRESS") || childTask.getState().equals("DONE"))) state = State.IN_PROGRESS;
            if(childTask.completed) doneCount++;
        }

        this.progressPrcnt = doneCount / totalCount;

        if(state == State.BLOCKED && (blockers == null || blockers.isEmpty())) state = State.IN_PROGRESS;

        if(progressPrcnt == 100) {state = State.DONE; completed = true;}

        TaskList.updateTask(this);

        return updateIssue;
    }

    private enum State {
        TO_DO, IN_PROGRESS, BLOCKED, DONE
    }
}
