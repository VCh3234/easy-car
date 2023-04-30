package by.easycar.model.administration;

import by.easycar.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "admins")
public class Admin implements UserDetails {

    public final static Role ROLE = Role.ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_sequence")
    @SequenceGenerator(catalog = "sequences", name = "adm_sequence", sequenceName = "admins_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "adm_id")
    private Long id;
    @Column(name = "adm_name", unique = true, nullable = false)
    private String name;
    @Column(name = "adm_password", nullable = false)
    private String password;

    @Transient
    private List<GrantedAuthority> authorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
