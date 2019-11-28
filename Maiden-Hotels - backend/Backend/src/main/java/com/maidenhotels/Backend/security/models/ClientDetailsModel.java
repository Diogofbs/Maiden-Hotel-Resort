package com.maidenhotels.Backend.security.models;

import com.maidenhotels.Backend.tibco.schemas.Client;
import com.maidenhotels.Backend.tibco.schemas.Guest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Details: How to get username, password, roles and permission from clients
public class ClientDetailsModel implements UserDetails {

    private Client client;
    private Guest guest;

    public ClientDetailsModel(Client client, Guest guest) {
        this.client = client;
        this.guest = guest;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        try{
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + "CLIENT");
            authorities.add(authority);
        }catch(Exception e){
            e.printStackTrace();

        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.client.getPassword();
    }

    @Override
    public String getUsername() {
        return this.guest.getEmail();
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
