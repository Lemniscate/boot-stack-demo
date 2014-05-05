package com.github.lemniscate.stack.boot.web.controller;

import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;

/**
 * @Author dave 4/25/14 1:08 PM
 */
@Controller
@Transactional
public class DummyController {

    private int i = 0;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/test")
    public @ResponseBody UserAccount person() {
        String password = BCrypt.hashpw("password", BCrypt.gensalt());
        UserAccount a = new UserAccount("dave", "welch", "email" + (i++), password, true, Collections.EMPTY_LIST);
        em.persist(a);
        return a;
    }

}
