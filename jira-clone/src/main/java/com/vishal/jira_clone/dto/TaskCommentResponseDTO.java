package com.vishal.jira_clone.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCommentResponseDTO {

    private Long commentId;
    private String message;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
}