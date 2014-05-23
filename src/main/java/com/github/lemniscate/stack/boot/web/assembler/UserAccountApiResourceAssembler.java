package com.github.lemniscate.stack.boot.web.assembler;

import com.github.lemniscate.lib.rest.mapping.ApiResourceAssembler;
import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.hateoas.Link;

import java.util.Collection;

//@Component
public class UserAccountApiResourceAssembler extends ApiResourceAssembler<UserAccount, Long, UserAccount> {

    @Override
    protected void doAddLinks(Collection<Link> links, UserAccount entity) {
        links.add( new Link("foobar", "http://google.com"));
    }
}
