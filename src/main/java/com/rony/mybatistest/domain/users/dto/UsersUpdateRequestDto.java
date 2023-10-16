package com.rony.mybatistest.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsersUpdateRequestDto {
    private String name;
    private String phoneNumber;
}