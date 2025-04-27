package com.gustavohc.springsimplebank.model;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @Column(name = "number", nullable = false, length = 8)
    private String number;

    @Column(name = "holder_name", nullable = false, length = 50)
    private String holderName;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankTransaction> transactions;

    @PrePersist
    private void setDefaultBalance() {
        if(balance == null) {
            balance = 0.0;
        }
    }
}
