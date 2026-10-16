package com.vishal.jira_clone.repository;

import com.vishal.jira_clone.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    List<TaskComment> findByTask_TaskIdOrderByCreatedAtAsc(Long taskId);
}