package com.gustavohc.springsimplebank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavohc.springsimplebank.model.BankTransaction;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, UUID> {

}
