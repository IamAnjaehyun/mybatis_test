package com.rony.mybatistest.domain.controller;

import com.rony.mybatistest.domain.users.dto.UsersCreateRequestDto;
import com.rony.mybatistest.domain.users.dto.UsersResponseDto;
import com.rony.mybatistest.domain.users.dto.UsersUpdateRequestDto;
import com.rony.mybatistest.domain.users.mapper.UsersMapper;
import com.rony.mybatistest.domain.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;
    private final UsersMapper usersMapper;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UsersCreateRequestDto usersCreateRequestDto) {
        usersMapper.createUsers(usersCreateRequestDto);
        return ResponseEntity.ok("회원가입이 성공적으로 이루어졌습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        usersService.deleteUsersById(id);
        return ResponseEntity.ok("회원 탈퇴가 정상적으로 이루어졌습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UsersUpdateRequestDto usersUpdateRequestDto) {
        usersService.updateUsers(id, usersUpdateRequestDto);
        return ResponseEntity.ok("회원정보 수정이 성공적으로 이루어졌습니다.");
    }

}
