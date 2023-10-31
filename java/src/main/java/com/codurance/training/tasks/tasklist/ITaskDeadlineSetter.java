package com.codurance.training.tasks.tasklist;

import java.time.LocalDate;

public interface ITaskDeadlineSetter {
    void setDeadline(String id, LocalDate deadline);
}