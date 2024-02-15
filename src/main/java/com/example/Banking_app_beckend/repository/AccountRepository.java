package com.example.Banking_app_beckend.repository;

import com.example.Banking_app_beckend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
