package com.rony.mybatistest.domain.users.mapper;

import com.rony.mybatistest.domain.users.dto.UsersCreateRequestDto;
import com.rony.mybatistest.domain.users.dto.UsersUpdateRequestDto;
import com.rony.mybatistest.domain.users.entitiy.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface UsersMapper {
    //회원 조회
    @Select("SELECT * FROM users where id =#{id}")
    Users findById(Long id);

    //회원 생성
    @Insert("INSERT INTO users (login_id, login_pw, name, phone_number) VALUES (#{loginId}, #{loginPw}, #{name}, #{phoneNumber})")
    void createUsers(UsersCreateRequestDto usersCreateRequestDto);

    //회원 수정
    @Update("UPDATE users SET name = #{usersUpdateRequestDto.name}, phone_number = #{usersUpdateRequestDto.phoneNumber} WHERE id = #{id}")
    void updateUsers(Long id, UsersUpdateRequestDto usersUpdateRequestDto);

    //회원 삭제
    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUsersById(Long id);
}
