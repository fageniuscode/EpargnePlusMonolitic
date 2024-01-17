package com.fageniuscode.epargneplus.api.entities;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ep_transaction")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date createDate;
    @Column(nullable = false)
    private String devise;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @ManyToOne
    @JoinColumn(name = "status_transaction_id")
    private StatusTransaction statusTransaction;
    @ManyToOne
    @JoinColumn(name = "type_transaction_id")
    private TypeTransaction typeTransaction;
    public Transaction(Long id, double amount, String description, Date createDate, String devise, Wallet wallet, StatusTransaction statusTransaction, TypeTransaction typeTransaction) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.createDate = createDate;
        this.devise = devise;
        this.wallet = wallet;
        this.statusTransaction = statusTransaction;
        this.typeTransaction = typeTransaction;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", devise='" + devise + '\'' +
                ", wallet=" + wallet +
                ", statusTransaction=" + statusTransaction +
                ", typeTransaction=" + typeTransaction +
                '}';
    }
}
