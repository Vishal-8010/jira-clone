package com.vishal.jira_clone.service;

import com.vishal.jira_clone.dto.TaskCommentRequestDTO;
import com.vishal.jira_clone.dto.TaskCommentResponseDTO;
import com.vishal.jira_clone.entity.Task;
import com.vishal.jira_clone.entity.TaskComment;
import com.vishal.jira_clone.entity.User;
import com.vishal.jira_clone.repository.TaskCommentRepository;
import com.vishal.jira_clone.repository.TaskRepository;
import com.vishal.jira_clone.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskCommentService {

    private final TaskCommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskCommentService(TaskCommentRepository commentRepository,
                              TaskRepository taskRepository,
                              UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Convert Entity to Response DTO
    private TaskCommentResponseDTO convertToDTO(TaskComment comment) {

        TaskCommentResponseDTO dto = new TaskCommentResponseDTO();

        dto.setCommentId(comment.getCommentId());
        dto.setMessage(comment.getMessage());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUserId(comment.getUser().getUserId());
        dto.setUserName(comment.getUser().getUserName());

        return dto;
    }

    // Add Comment
    public TaskCommentResponseDTO addComment(
            Long taskId, Long userId, TaskCommentRequestDTO request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        if (request == null || request.getMessage() == null
                || request.getMessage().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Comment cannot be empty");
        }

        TaskComment comment = new TaskComment();

        comment.setMessage(request.getMessage());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTask(task);
        comment.setUser(user);

        return convertToDTO(commentRepository.save(comment));
    }

    // Get Comments of a Task
    public List<TaskCommentResponseDTO> getCommentsByTask(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Task not found");
        }

        return commentRepository
                .findByTask_TaskIdOrderByCreatedAtAsc(taskId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Update Comment
    public TaskCommentResponseDTO updateComment(
            Long commentId, Long userId, TaskCommentRequestDTO request) {

        TaskComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot edit this comment");
        }

        if (request == null || request.getMessage() == null
                || request.getMessage().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Comment cannot be empty");
        }

        comment.setMessage(request.getMessage());

        return convertToDTO(commentRepository.save(comment));
    }

    // Delete Comment
    public void deleteComment(Long commentId, Long userId) {

        TaskComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot delete this comment");
        }

        commentRepository.delete(comment);
    }
}