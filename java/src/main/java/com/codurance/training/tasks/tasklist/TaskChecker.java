package com.codurance.training.tasks.tasklist;
import com.codurance.training.tasks.task.ITask;
import java.util.List;
import java.util.Map;

public class TaskChecker implements ITaskChecker {
    private final Map<String, List<ITask>> tasks;

    public TaskChecker(Map<String, List<ITask>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void check(String idString) {
        setDone(idString, true);
    }

    @Override
    public void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<ITask>> project : tasks.entrySet()) {
            for (ITask task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        System.out.printf("Could not find a task with an ID of %d.%n", id);
    }
}