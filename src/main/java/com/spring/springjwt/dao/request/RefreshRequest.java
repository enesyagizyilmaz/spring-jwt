package com.spring.springjwt.dao.request;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;
}
