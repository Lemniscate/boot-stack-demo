package com.github.lemniscate.stack.boot.svc;


import com.github.lemniscate.lib.sra.repo.ResourceRepository;
import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;


@Component
@Transactional(propagation= Propagation.MANDATORY)
public class UserService implements UserDetailsService, Serializable {

    @Inject
    private ResourceRepository<UserAccount, Long> repo;

    public UserAccount save(UserAccount user){
        return repo.save(user);
    }

    public List<UserAccount> findAll() {
        List<UserAccount> users = repo.findAll();
        return users;
    }

    /**
     * Look up a user by email (which is our "username" in this case)
     */
    @Override
    // We need to support new transactions here because Spring Security doesn't go through our annotated controller
    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public UserAccount loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = null; // repo.findByEmail(email);
        if( user == null){
            throw new UsernameNotFoundException("Could not find user by username " + email);
        }
        return user;
    }

    public UserAccount getCurrentUser(){
        UserAccount user = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth != null ){
            Object p = auth.getPrincipal();
            if( p != null && p instanceof UserAccount ){
                user = (UserAccount) p;
            }
        }
        return user;
    }

    public boolean isLoggedIn(){
        return getCurrentUser() != null;
    }

}

