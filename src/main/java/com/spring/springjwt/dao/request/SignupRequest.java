package com.spring.springjwt.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
