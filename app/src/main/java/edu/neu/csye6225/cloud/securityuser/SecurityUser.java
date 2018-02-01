package edu.neu.csye6225.cloud.securityuser;

import edu.neu.csye6225.cloud.enums.Role;
import edu.neu.csye6225.cloud.modal.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser extends User implements UserDetails{

    private static final long serialVersionUID = 1L;

    public SecurityUser(User user) {
        super(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role roles = super.getRole();
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
