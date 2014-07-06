package com.github.lemniscate.stack.boot.web.assembler;

import com.github.lemniscate.lib.tiered.controller.ApiResourceNestedCollectionController;
import com.github.lemniscate.lib.tiered.controller.ApiResourceNestedPropertyController;
import com.github.lemniscate.lib.tiered.mapping.ApiResourceAssembler;
import com.github.lemniscate.lib.tiered.mapping.ApiResourceLinkBuilderFactory;
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
    private ApiResourceNestedCollectionController<UserDevice, Long, UserDevice, UserAccount, Long> devicesController;

    @Inject
    private ApiResourceNestedPropertyController<UserSettings, Long, UserSettings, UserAccount, Long> settingsController;

    @Inject
    private ApiResourceLinkBuilderFactory arLinkBuilder;

    @Override
    public void addLinks(Collection<Link> links, UserAccount user) {
        links.add(arLinkBuilder.linkTo(devicesController.getClass(), UserDevice.class, user.getId()).withRel("devices"));
        links.add(arLinkBuilder.linkTo(settingsController.getClass(), UserSettings.class, user.getId()).withRel("settings"));
    }

}