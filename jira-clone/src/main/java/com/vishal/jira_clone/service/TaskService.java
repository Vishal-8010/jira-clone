package com.vishal.jira_clone.service;

import com.vishal.jira_clone.entity.Project;
import com.vishal.jira_clone.entity.Task;
import com.vishal.jira_clone.entity.User;
import com.vishal.jira_clone.repository.ProjectRepository;
import com.vishal.jira_clone.repository.TaskRepository;
import com.vishal.jira_clone.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.vishal.jira_clone.enums.TaskStatus;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task, Long projectId){
        Project project = projectRepository.findById(projectId).orElse(null);

        if(project == null){
            return null;
        }

        task.setProject(project);
        return taskRepository.save(task);
    }

    public Task assignTask(Long taskId, Long userId){

        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null) return null;

        User user = userRepository.findById(userId).orElse(null);
        if(user == null) return null;

        task.setAssignee(user);
        return taskRepository.save(task);

    }

    public Task updateTaskStatus(Long taskId, TaskStatus status){
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null) return null;
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(Long userId){
        return taskRepository.findByAssignee_UserId(userId);
    }

    public List<Task> getTasksByProject(Long projectId){
        return taskRepository.findByProject_ProjectId(projectId);
    }

    public List<Task> getTasksByStatus(Long projectId, TaskStatus status){
        return taskRepository.findByProject_ProjectIdAndStatus(projectId, status);
    }



}
