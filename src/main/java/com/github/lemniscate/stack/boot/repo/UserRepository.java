package com.github.lemniscate.stack.boot.repo;

import com.github.lemniscate.spring.crud.repo.ApiResourceRepository;
import com.github.lemniscate.stack.boot.model.UserAccount;


public interface UserRepository extends ApiResourceRepository<Long, UserAccount> {

}
