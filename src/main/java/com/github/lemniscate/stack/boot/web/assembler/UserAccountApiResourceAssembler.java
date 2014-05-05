package com.github.lemniscate.stack.boot.web.assembler;

import com.github.lemniscate.lib.sra.mapping.AbstractApiResourceAssembler;
import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;

//@Component
public class UserAccountApiResourceAssembler extends AbstractApiResourceAssembler<UserAccount> {

    @Override
    protected void doAddLinks(Collection<Link> links, UserAccount entity) {
        links.add( new Link("foobar", "http://google.com"));
    }
}
