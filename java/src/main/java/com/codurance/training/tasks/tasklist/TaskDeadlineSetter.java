package com.codurance.training.tasks.tasklist;

import com.codurance.training.tasks.task.ITask;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskDeadlineSetter implements ITaskDeadlineSetter{
    private final Map<String, List<ITask>> tasks = new LinkedHashMap<>();
    @Override
    public void setDeadline(String id, LocalDate deadline) {
        String taskId;
        for (List<ITask> projectTasks : tasks.values()) {
            for (ITask task : projectTasks) {
                taskId = String.valueOf(task.getId());
                if (taskId.equals(id)) {
                    task.setDeadline(deadline);
                    return;
                }
            }
        }
        System.out.printf("Could not find a task with an ID of %s.%n", id);
    }
}
