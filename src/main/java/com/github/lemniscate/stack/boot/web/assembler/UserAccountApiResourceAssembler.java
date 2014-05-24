package com.github.lemniscate.stack.boot.web.assembler;

import com.github.lemniscate.lib.rest.controller.ApiResourceNestedCollectionController;
import com.github.lemniscate.lib.rest.controller.ApiResourceNestedPropertyController;
import com.github.lemniscate.lib.rest.mapping.ApiResourceAssembler;
import com.github.lemniscate.lib.rest.mapping.ApiResourceLinkBuilderFactory;
import com.github.lemniscate.stack.boot.model.UserAccount;
import com.github.lemniscate.stack.boot.model.UserDevice;
import com.github.lemniscate.stack.boot.model.UserSettings;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;

@Component
public class UserAccountApiResourceAssembler extends ApiResourceAssembler<UserAccount, Long, UserAccount> {

    @Inject
    private ApiResourceNestedCollectionController<UserDevice, Long, UserDevice, UserAccount> devicesController;

    @Inject
    private ApiResourceNestedPropertyController<UserSettings, Long, UserSettings, UserAccount> settingsController;

    @Inject
    private ApiResourceLinkBuilderFactory arLinkBuilder;

    @Override
    public void addLinks(Collection<Link> links, UserAccount user) {
        links.add(arLinkBuilder.linkTo(devicesController.getClass(), UserDevice.class, user.getId()).withRel("devices"));
        links.add(arLinkBuilder.linkTo(settingsController.getClass(), UserSettings.class, user.getId()).withRel("settings"));
    }

}