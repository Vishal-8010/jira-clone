package com.vishal.jira_clone.service;


import com.vishal.jira_clone.entity.Project;
import com.vishal.jira_clone.entity.User;
import com.vishal.jira_clone.repository.ProjectRepository;
import com.vishal.jira_clone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
        private final ProjectRepository projectRepository;
        private final UserRepository userRepository;

        public ProjectService(ProjectRepository projectRepository, UserRepository userRepository){
            this.projectRepository = projectRepository;
            this.userRepository = userRepository;
        }

        public Project createProject(Long id, Project project){
            User user = userRepository.findById(id).orElse(null);
            if(user==null) return null;
            project.setOwner(user);
            return projectRepository.save(project);
        }
}
