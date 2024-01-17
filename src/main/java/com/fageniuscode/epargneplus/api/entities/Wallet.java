package com.fageniuscode.epargneplus.api.entities;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "ep_wallet")
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String walletNumber;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private Date lastUpdateDate;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private int lockingTime;
    @Column(nullable = false)
    private String walletStatus;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "interest_rate_id")
    private InterestRate interestRate;

    public Wallet(Long id, String walletNumber, double amount, Date createDate, Date lastUpdateDate, String username, int lockingTime, String walletStatus, List<Transaction> transactions, InterestRate interestRate) {
        this.id = id;
        this.walletNumber = walletNumber;
        this.amount = amount;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.username = username;
        this.lockingTime = lockingTime;
        this.walletStatus = walletStatus;
        this.transactions = transactions;
        this.interestRate = interestRate;
    }

    public Wallet(Long id, String walletNumber, double amount, Date createDate, Date lastUpdateDate, String username, int lockingTime, String walletStatus, InterestRate interestRate) {
        this.id = id;
        this.walletNumber = walletNumber;
        this.amount = amount;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.username = username;
        this.lockingTime = lockingTime;
        this.walletStatus = walletStatus;
        this.interestRate = interestRate;
    }

    public Wallet(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", walletNumber='" + walletNumber + '\'' +
                ", amount=" + amount +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", username='" + username + '\'' +
                ", lockingTime=" + lockingTime +
                ", walletStatus='" + walletStatus + '\'' +
                ", interestRate=" + interestRate +
                '}';
    }
}
