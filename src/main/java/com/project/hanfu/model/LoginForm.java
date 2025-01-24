package com.project.hanfu.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginForm {
    private String account;
    private String password;
    private String role;
}
