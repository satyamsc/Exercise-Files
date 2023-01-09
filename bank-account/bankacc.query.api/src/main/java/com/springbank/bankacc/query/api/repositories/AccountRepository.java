package com.springbank.bankacc.query.api.repositories;

import com.springbank.bankacc.core.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByBalanceLessThan(double balance);
}
