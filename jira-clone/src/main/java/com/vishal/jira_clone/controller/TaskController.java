package com.vishal.jira_clone.controller;

import com.vishal.jira_clone.entity.Task;
import com.vishal.jira_clone.enums.TaskStatus;
import com.vishal.jira_clone.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/project/{projectId}")
    public Task createTask(@RequestBody Task task, @PathVariable Long projectId){
        return taskService.createTask(task, projectId);
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public Task assingTask(@PathVariable Long taskId, @PathVariable Long userId){
        return taskService.assignTask(taskId,userId);
    }

    @PutMapping("{taskId}/status/{status}")
    public Task updateTaskStatus(@PathVariable Long taskId, @PathVariable TaskStatus status){
        return taskService.updateTaskStatus(taskId, status);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId) {

        return taskService.getTasksByUser(userId);

    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable Long projectId){
        return taskService.getTasksByProject(projectId);
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public List<Task> getTaskyStatus(@PathVariable Long projectId, @PathVariable TaskStatus status ){
        return taskService.getTasksByStatus(projectId, status);
    }
}
