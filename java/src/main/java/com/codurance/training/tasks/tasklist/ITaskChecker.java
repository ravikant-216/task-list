package com.codurance.training.tasks.tasklist;

public interface ITaskChecker {
    void check(String idString);
    void uncheck(String idString);
}