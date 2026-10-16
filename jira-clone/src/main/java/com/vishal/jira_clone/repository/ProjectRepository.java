package com.vishal.jira_clone.repository;

import com.vishal.jira_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
