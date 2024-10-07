package com.example.yozmhealth.users.controller;

import com.example.yozmhealth.domain.dto.AccountDto;
import com.example.yozmhealth.domain.entity.Account;
import com.example.yozmhealth.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(AccountDto accountDto){  // modelmapper 는 매개변수에 입력된 정보를 다른 객체에 복사해준다
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(accountDto, Account.class);  // 객체, 최종값들을 받을 클래스 타입
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userService.createUser(account);

        return "redirect:/";
    }
}
