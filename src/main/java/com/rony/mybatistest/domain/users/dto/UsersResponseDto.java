package com.rony.mybatistest.domain.users.dto;

import com.rony.mybatistest.domain.users.entitiy.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersResponseDto {
    private String loginId;
    private String name;
    private String phoneNumber;

    public static UsersResponseDto convertUserToUsersResponse(Users users) {
        return UsersResponseDto.builder()
                .loginId(users.getLoginId())
                .name(users.getName())
                .phoneNumber(users.getPhoneNumber())
                .build();
    }
}
