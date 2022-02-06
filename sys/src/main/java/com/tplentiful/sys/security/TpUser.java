package com.tplentiful.sys.security;

import com.tplentiful.sys.pojo.po.Perm;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class TpUser implements UserDetails {
    private List<GrantedAuthority> authorities;

    private String password;
    private String username;
    private String salt;
    private Boolean locked = false;
    private Boolean valid = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Perm> perms) {
        if (CollectionUtils.isEmpty(authorities)) {
            authorities = new LinkedList<>();
        }
        for (Perm perm : perms) {
            authorities.add(new SimpleGrantedAuthority(perm.getName()));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !valid;
    }
}
