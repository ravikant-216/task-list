package com.codurance.training.tasks.tasklist;
import com.codurance.training.tasks.task.ITask;
import com.codurance.training.tasks.task.Task;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskManager implements ITaskManager,ITaskDelete  {
    private final Map<String, List<ITask>> tasks = new LinkedHashMap<>();
    private long lastId = 0;

    @Override
    public void addTask(String project, String description) {
        List<ITask> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            System.out.printf("Could not find a project with the name \"%s\".%n", project);
            return;
        }
        projectTasks.add(new Task(nextId(), description, false));
    }

    @Override
    public Map<String, List<ITask>> getTasks() {
        return tasks;
    }

    public void deleteTask(String id) {
        String taskId;
        for (List<ITask> projectTasks : tasks.values()) {
            for (ITask task : projectTasks) {
                taskId = String.valueOf(task.getId());
                if (taskId.equals(id)) {
                    projectTasks.remove(task);
                    return;
                }
            }
        }
        System.out.printf("Could not find a task with an ID of %s.%n", id);
    }


    private long nextId() {
        return ++lastId;
    }
}