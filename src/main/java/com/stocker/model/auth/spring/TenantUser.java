package com.stocker.model.auth.spring;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * A Spring security {@link User} with additional tenant id. 
 */
public class TenantUser extends User
{
    private static final long serialVersionUID = -4489363491324783691L;

    private final Integer tenant;

    public TenantUser(String username, String password, boolean enabled, Integer tenant,
        Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, true, true, true, authorities);
        this.tenant = tenant;
    }

    /**
     * @return the tenant id of the current user
     */
    public Integer getTenant()
    {
        return tenant;
    }
}
