package com.codurance.training.tasks.tasklist;
import com.codurance.training.tasks.task.ITask;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectManager implements IProjectManager {
    private final Map<String, List<ITask>> tasks;

    public ProjectManager(Map<String, List<ITask>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void addProject(String name) {
        tasks.put(name, new ArrayList<>());
    }
}