package com.codurance.training.tasks.executor;

import com.codurance.training.tasks.task.ITask;
import com.codurance.training.tasks.tasklist.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CommandExecutor implements ICommandExecutor {
    private final BufferedReader in;
    private final PrintWriter out;
    private final ITaskManager taskManager;
    private final ITaskDelete taskDelete;
    private final ITaskDeadlineSetter taskDeadlineSetter;
    private final ITaskChecker taskChecker;
    private final IProjectManager projectManager;

    public CommandExecutor(BufferedReader reader, PrintWriter writer, ITaskManager taskManager, ITaskChecker taskChecker, IProjectManager projectManager, ITaskDeadlineSetter taskDeadlineSetter, ITaskDelete taskDelete) {
        this.in = reader;
        this.out = writer;
        this.taskManager = taskManager;
        this.taskChecker = taskChecker;
        this.projectManager = projectManager;
        this.taskDeadlineSetter = taskDeadlineSetter;
        this.taskDelete = taskDelete;
    }

    public void executeCommand(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                taskChecker.check(commandRest[1]);
                break;
            case "uncheck":
                taskChecker.uncheck(commandRest[1]);
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        Map<String, List<ITask>> tasks = taskManager.getTasks();
        for (Map.Entry<String, List<ITask>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (ITask task : project.getValue()) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            projectManager.addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            taskManager.addTask(projectTask[0], projectTask[1]);
        }
    }

    private void error(String command) {
        out.printf("Error: \"%s\" is not a valid command.%n", command);
        out.println();
    }
    private void setDeadline(String commandLine) {
        String[] parts = commandLine.split(" ", 2);
        String id = parts[0];
        LocalDate deadline = LocalDate.parse(parts[1]);
        taskDeadlineSetter.setDeadline(id, deadline);
    }

    private void delete(String id) {
        taskDelete.deleteTask(id);
    }

    private void viewByDate() {
        Map<String, List<ITask>> tasks = taskManager.getTasks();
        tasks.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(ITask::getDeadline))
                .forEach(task -> out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription()));
    }

    private void viewByDeadline() {
        LocalDate today = LocalDate.now();
        Map<String, List<ITask>> tasks = taskManager.getTasks();
        tasks.values().stream()
                .flatMap(List::stream)
                .filter(task -> today.equals(task.getDeadline()))
                .forEach(task -> out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription()));
    }

    private void viewByProject() {
        Map<String, List<ITask>> tasks = taskManager.getTasks();
        for (Map.Entry<String, List<ITask>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (ITask task : project.getValue()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

}
