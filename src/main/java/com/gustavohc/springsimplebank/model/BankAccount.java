package com.gustavohc.springsimplebank.model;

import java.util.List;

import org.hibernate.annotations.Check;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_account")
@Check(constraints = "balance >= 0")
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
    @Min(value = 0, message = "Balance cannot be negative")
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
