package com.rony.mybatistest.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsersCreateRequestDto {
    private String loginId;
    private String loginPw;
    private String name;
    private String phoneNumber;
}
