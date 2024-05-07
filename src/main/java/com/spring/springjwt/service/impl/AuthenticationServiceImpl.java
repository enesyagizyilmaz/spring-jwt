package com.spring.springjwt.service.impl;

import com.spring.springjwt.dao.request.RefreshRequest;
import com.spring.springjwt.dao.request.SigninRequest;
import com.spring.springjwt.dao.request.SignupRequest;
import com.spring.springjwt.dao.response.JwtAuthenticationResponse;
import com.spring.springjwt.entities.RefreshToken;
import com.spring.springjwt.entities.Role;
import com.spring.springjwt.entities.User;
import com.spring.springjwt.repository.UserRepository;
import com.spring.springjwt.service.AuthenticationService;
import com.spring.springjwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        var user = User.builder()/*.firstName(request.getFirstName()).lastName(request.getLastName())*/
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshTokenService.createRefreshToken(user)).userId(user.getId()).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshTokenService.createRefreshToken(user)).userId(user.getId()).build();
    }

    @Override
    public JwtAuthenticationResponse refresh(RefreshRequest refreshRequest)
    {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());

        if (token.getToken().equals(refreshRequest.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token))
        {
            User user = token.getUser();
            String jwtToken = jwtService.generateToken(user);
            response.setAccessToken(jwtToken);
            response.setUserId(user.getId());
            return response;
        }
        else
        {
            throw new RuntimeException("exception");
        }
    }
}
