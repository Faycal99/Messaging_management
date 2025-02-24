package dgb.Mp.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Setter
public class SecurityUser implements UserDetails {

    private final User user;



    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Return the user's authorities (roles and privileges)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getPrivileges().stream()
                .map(privilege -> (new SimpleGrantedAuthority(privilege.getName().name())))
                .collect(Collectors.toSet());// Correct placement of parentheses

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
