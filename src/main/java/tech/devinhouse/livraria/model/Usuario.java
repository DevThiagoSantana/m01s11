package tech.devinhouse.livraria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "USUARIOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;

    private String email;

    private String senha;

    private LocalDate dataNascimento;

    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "USUARIOS_ROLES", joinColumns = @JoinColumn(name = "ID_USUARIO"))
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null)
            return null;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role: this.roles) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role.name());
            authorities.add(sga);
        }
//        List<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
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
