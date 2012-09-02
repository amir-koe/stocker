package com.stocker.model.auth.spring;

import javax.inject.Inject;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stocker.model.auth.User;
import com.stocker.model.auth.UserRepository;

/**
 * {@link org.springframework.security.core.userdetails.UserDetailsService} delegating to {@link UserRepository}
 */
@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{
    private final UserRepository userRepository;

    @Inject
    public UserDetailsService(UserRepository userRepository)
    {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);

        return user == null ? null : new TenantUser(
            user.getUsername(), user.getPassword(), true, user.getTenantId(), AuthorityUtils.NO_AUTHORITIES);
    }
}
