package com.codurance.training.tasks.task;

import java.time.LocalDate;

public interface ITask {
    long getId();
    String getDescription();
    boolean isDone();
    void setDone(boolean done);
    public void setDeadline(LocalDate deadline);
    public LocalDate getDeadline();
}