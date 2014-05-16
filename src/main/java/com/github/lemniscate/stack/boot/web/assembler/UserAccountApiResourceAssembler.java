package com.github.lemniscate.stack.boot.web.assembler;

import com.github.lemniscate.lib.rest.controller.ApiResourceController;
import com.github.lemniscate.lib.rest.mapping.AbstractApiResourceAssembler;
import com.github.lemniscate.stack.boot.model.UserAccount;
import org.springframework.hateoas.Link;

import javax.inject.Inject;
import java.util.Collection;

//@Component
public class UserAccountApiResourceAssembler extends AbstractApiResourceAssembler<UserAccount, Long, UserAccount> {

    @Override
    protected void doAddLinks(Collection<Link> links, UserAccount entity) {
        links.add( new Link("foobar", "http://google.com"));
    }
}
