package com.maidenhotels.Backend.security.models;

import com.maidenhotels.Backend.tibco.schemas.Backoffice;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Details: How to get username, password, roles and permission from employees
public class BackofficeDetailsModel implements UserDetails {

    /*@Autowired
    private Roles_services roles;*/

    private Backoffice backoffice;

    public BackofficeDetailsModel(Backoffice backoffice) {
        this.backoffice = backoffice;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        try{

            /*Role role = new Role(this.backoffice.getIdRole(),"");
            Roles request = new Roles();
            request.getRole().add(role);
            List<Role> list = roles.getByID(request);
            Role roleFinal = list.get(0);
            String roleName = roleFinal.getName();*/

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"  + this.backoffice.getIdRole());
            authorities.add(authority);
        }catch(Exception e){
            e.printStackTrace();

        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.backoffice.getPassword();
    }

    @Override
    public String getUsername() {
        return this.backoffice.getUsername();
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
