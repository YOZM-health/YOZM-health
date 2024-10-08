package com.example.yozmhealth.users.repository;


import com.example.yozmhealth.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
