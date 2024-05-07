package com.spring.springjwt.service;

import com.spring.springjwt.dao.request.RefreshRequest;
import com.spring.springjwt.dao.request.SigninRequest;
import com.spring.springjwt.dao.request.SignupRequest;
import com.spring.springjwt.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    JwtAuthenticationResponse refresh(RefreshRequest request);
}
