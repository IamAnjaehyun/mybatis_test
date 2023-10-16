package com.rony.mybatistest.domain.users.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    private Long id;
    private String loginId;
    private String loginPw;
    private String name;
    private String phoneNumber;

}
