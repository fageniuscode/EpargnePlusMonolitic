package com.fageniuscode.epargneplus.api.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Table(name = "ep_type_transaction")
@NoArgsConstructor
@Getter
@Setter
public class TypeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le nom doit avoir entre 2 et 150 caractères")
    private String name;

    @NotNull(message = "La description ne peut pas être vide")
    @Size(min = 2, max = 250, message = "La description doit avoir entre 2 et 250 caractères")
    private String description;

    @OneToMany(mappedBy = "typeTransaction")
    private Set<Transaction> transactions;

    public TypeTransaction(Long id, String name, String description, Set<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.transactions = transactions;
    }

    public TypeTransaction(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TypeTransaction(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TypeTransaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
