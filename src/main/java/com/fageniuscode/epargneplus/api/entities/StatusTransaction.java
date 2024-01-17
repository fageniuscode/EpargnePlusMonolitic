package com.fageniuscode.epargneplus.api.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "ep_status_transaction")
@NoArgsConstructor
@Getter
@Setter
public class StatusTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le nom doit avoir entre 2 et 150 caractères")
    private String name;
    @NotNull(message = "Le commentaire ne peut pas être vide")
    @Size(min = 2, max = 250, message = "Le commentaire doit avoir entre 2 et 250 caractères")
    private String comment;

    private Date lastUpdateDate;
    @OneToMany(mappedBy = "statusTransaction")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Transaction> transactions;

    public StatusTransaction(Long id, String name, String comment, Date lastUpdateDate, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.lastUpdateDate = lastUpdateDate;
        this.transactions = transactions;
    }

    public StatusTransaction(Long id, String name, String comment, Date lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.lastUpdateDate = lastUpdateDate;
    }

    public StatusTransaction(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TransactionStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
