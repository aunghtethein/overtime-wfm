package com.ot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.ot.model.Staff;

import java.util.Collection;


public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;
	private final Staff staff;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Staff staff) {
        this.staff = staff;
		 
        String[] privileges = staff.getRoles().stream()
                .flatMap(role -> role.getPrivileges().stream())
                .map(privilege -> privilege.getName())
                .toArray(String[]::new);

        this.authorities = AuthorityUtils.createAuthorityList(privileges);
    }

    public Staff getStaff() {
        return staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getStaffId();
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
