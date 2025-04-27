package com.gustavohc.springsimplebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavohc.springsimplebank.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
