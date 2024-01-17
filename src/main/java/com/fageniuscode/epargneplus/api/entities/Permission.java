package com.fageniuscode.epargneplus.api.entities;

import com.fageniuscode.epargneplus.api.enumeration.PermissionType;
import com.fageniuscode.epargneplus.api.enumeration.SecurityAction;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
@Entity
@Table(name = "ep_permission")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonFilter("com.fageniuscode.as.api.Permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;
    private String name;
    private SecurityAction securityAction;
    private String entityName;
    PermissionType permissionType;
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Permission rhs = (Permission) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .build();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(name).
                toHashCode();
    }
}
