package com.github.lemniscate.stack.boot.svc;


import com.github.lemniscate.spring.crud.repo.ApiResourceRepository;
import com.github.lemniscate.spring.crud.svc.ApiResourceServiceImpl;
import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.io.Serializable;


@Component
@Transactional(propagation= Propagation.MANDATORY)
public class UserService extends ApiResourceServiceImpl<Long, UserAccount, UserAccount, UserAccount, UserAccount> implements UserDetailsService, Serializable {

    @Inject
    private ApiResourceRepository<Long, UserAccount> repo;

    @Override
    public Page<UserAccount> query(MultiValueMap<String, String> params, Pageable pageable) {
        return super.query(params, pageable);
    }

    public UserAccount save(UserAccount user){
        return repo.save(user);
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

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Cannot delete users. Instead set them to inactive");
    }
}

