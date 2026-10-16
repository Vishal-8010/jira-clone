package com.vishal.jira_clone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String message;
    private Long userId;
    private String name;
    private String email;
    private String token;

}