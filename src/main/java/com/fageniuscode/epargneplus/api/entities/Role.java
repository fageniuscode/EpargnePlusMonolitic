package com.fageniuscode.epargneplus.api.entities;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
@Entity
@Data
@Table(name = "ep_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonFilter("com.fageniuscode.as.api.Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;
    private String name;
    private String displayName;
    private boolean visible;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ep_role_permission",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;
    @ManyToMany(mappedBy = "rolesEntities")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> userEntities;
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", visible=" + visible +
                '}';
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
        Role rhs = (Role) obj;
        return new EqualsBuilder()
                .append(name.toLowerCase(), rhs.name.toLowerCase())
                .build();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(name.toLowerCase()).
                toHashCode();
    }
    public void sortPermissions() {
        if (this.permissions != null) {
            this.permissions.sort(new Comparator<Permission>() {
                @Override
                public int compare(Permission o1, Permission o2) {
                    if (o1.getName() == null && o2.getName() == null) {
                        return 0;
                    }
                    if (o1.getName() == null && o2.getName() != null) {
                        return -1;
                    }
                    if (o1.getName() != null && o2.getName() == null) {
                        return 1;
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }
}

