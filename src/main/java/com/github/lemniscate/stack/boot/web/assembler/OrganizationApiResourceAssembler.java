//package com.github.lemniscate.stack.boot.web.assembler;
//
//import com.github.lemniscate.lib.tiered.controller.ApiResourceController;
//import com.github.lemniscate.lib.tiered.mapping.ApiResourceAssembler;
//import com.github.lemniscate.stack.boot.model.Organization;
//import org.springframework.hateoas.Link;
//import org.springframework.stereotype.Component;
//
//import javax.inject.Inject;
//import java.util.Collection;
//
//@Component
//public class OrganizationApiResourceAssembler extends ApiResourceAssembler<Organization, Long, Organization> {
//
//    @Override
//    public void addLinks(Collection<Link> links, Organization entity) {
//        links.add( new Link("http://organization.com", "homepage"));
//    }
//
//}
