package com.kt.cloud.mqmgmt.tokenservice;

import org.springframework.stereotype.Component;

public interface TokenService {
    public String getToken(String userId, String userPw);
}
