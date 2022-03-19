package com.kt.cloud.mqmgmt.tokenservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForm {
    private String userId;
    private String userPw;
}
