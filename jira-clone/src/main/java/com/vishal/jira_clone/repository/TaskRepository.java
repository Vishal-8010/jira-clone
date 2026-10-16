package com.vishal.jira_clone.repository;


import com.vishal.jira_clone.entity.Project;
import com.vishal.jira_clone.entity.Task;
import com.vishal.jira_clone.enums.TaskPriority;
import com.vishal.jira_clone.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignee_UserId(Long userId);
    List<Task> findByProject_ProjectId(Long projectId);

    // find particular task for a project
    List<Task> findByProject_ProjectIdAndStatus(Long projectId, TaskStatus status);

    List<Task> findByProject_ProjectIdAndPriority(Long projectId, TaskPriority taskPriority);
}
