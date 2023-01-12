package com.bilgeadam.basurveyapp.controller;

import com.bilgeadam.basurveyapp.entity.User;
import com.bilgeadam.basurveyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    ResponseEntity<List<User>> getUserList(){
        return ResponseEntity.ok(userService.getUserList());
    }
    @GetMapping("/page")
    ResponseEntity<Page<User>> getUserPage(Pageable pageable){
        return ResponseEntity.ok(userService.getUserPage(pageable));
    }
}