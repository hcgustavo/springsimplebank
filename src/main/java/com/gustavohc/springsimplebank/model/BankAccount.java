package com.gustavohc.springsimplebank.model;

import java.util.List;
import java.util.Random;

import org.hibernate.annotations.Check;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
    @OrderBy("timestamp DESC")
    private List<BankTransaction> transactions;

    @PrePersist
    private void onPersist() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        if(balance == null) {
            balance = 0.0;
        }

        for(int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }

        number = result.toString();
    }
}
