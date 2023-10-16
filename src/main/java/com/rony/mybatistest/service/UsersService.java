package com.rony.mybatistest.service;

import com.rony.mybatistest.domain.users.dto.UsersResponseDto;
import com.rony.mybatistest.domain.users.dto.UsersUpdateRequestDto;
import com.rony.mybatistest.domain.users.entitiy.Users;
import com.rony.mybatistest.domain.users.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersMapper usersMapper;

    public UsersResponseDto findUserById(Long id){
        Users user = usersMapper.findById(id);
        return UsersResponseDto.convertUserToUsersResponse(user);
    }

    public void deleteUsersById(Long id) {
        //TODO : security 적용 후 principal 이용하여 본인인지 확인 후 삭제하는 로직 추가 예정

        usersMapper.deleteUsersById(id);
    }

    public void updateUsers(Long id, UsersUpdateRequestDto usersUpdateRequestDto) {
        //TODO : security 적용 후 principal 이용하여 본인인지 확인 후 수정하는 로직 추가 예정

        usersMapper.updateUsers(id, usersUpdateRequestDto);
    }
}
