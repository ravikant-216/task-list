package com.codurance.training.tasks.tasklist;
import com.codurance.training.tasks.task.ITask;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ITaskManager {
    void addTask(String project, String description);
    Map<String, List<ITask>> getTasks();

}