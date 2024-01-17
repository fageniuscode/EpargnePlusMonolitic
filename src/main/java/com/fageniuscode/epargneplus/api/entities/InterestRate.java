package com.fageniuscode.epargneplus.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "ep_interestRate")
@NoArgsConstructor
@Getter
@Setter
public class InterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "interestRate")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Wallet> wallets;

    public InterestRate(Long id, double rate, String description, List<Wallet> wallets) {
        this.id = id;
        this.rate = rate;
        this.description = description;
        this.wallets = wallets;
    }

    public InterestRate(Long id, double rate, String description) {
        this.id = id;
        this.rate = rate;
        this.description = description;
    }

    public InterestRate(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InterestRate{" +
                "id=" + id +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                '}';
    }
}
