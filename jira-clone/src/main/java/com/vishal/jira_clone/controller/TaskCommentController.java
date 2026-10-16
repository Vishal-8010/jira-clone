package com.vishal.jira_clone.controller;

import com.vishal.jira_clone.dto.TaskCommentRequestDTO;
import com.vishal.jira_clone.dto.TaskCommentResponseDTO;
import com.vishal.jira_clone.service.TaskCommentService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class TaskCommentController {

    private final TaskCommentService commentService;

    public TaskCommentController(TaskCommentService commentService) {
        this.commentService = commentService;
    }

    // Add Comment
    @PostMapping("/task/{taskId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskCommentResponseDTO addComment(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody TaskCommentRequestDTO request) {

        return commentService.addComment(taskId, userId, request);
    }

    // Get Comments
    @GetMapping("/task/{taskId}")
    public List<TaskCommentResponseDTO> getComments(
            @PathVariable Long taskId) {

        return commentService.getCommentsByTask(taskId);
    }

    // Update Comment
    @PutMapping("/{commentId}/user/{userId}")
    public TaskCommentResponseDTO updateComment(
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @RequestBody TaskCommentRequestDTO request) {

        return commentService.updateComment(commentId, userId, request);
    }

    // Delete Comment
    @DeleteMapping("/{commentId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        commentService.deleteComment(commentId, userId);
    }
}