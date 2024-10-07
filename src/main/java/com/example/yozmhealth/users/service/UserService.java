package com.example.yozmhealth.users.service;

import com.example.yozmhealth.domain.entity.Account;
import com.example.yozmhealth.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(Account account){
//        userRepository.save(account);
        Account savedAccount = userRepository.save(account);
        System.out.println("Saved Account: " + savedAccount);
    }
}
