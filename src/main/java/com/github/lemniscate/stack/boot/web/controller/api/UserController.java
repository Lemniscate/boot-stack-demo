package com.github.lemniscate.stack.boot.web.controller.api;

import com.github.lemniscate.spring.crud.annotation.AssembleWith;
import com.github.lemniscate.spring.crud.mapping.ApiResourceMapping;
import com.github.lemniscate.spring.crud.svc.ApiResourceService;
import com.github.lemniscate.spring.crud.web.ApiResourceController;
import com.github.lemniscate.spring.crud.web.assembler.ApiResourceAssembler;
import com.github.lemniscate.stack.boot.model.UserAccount;
import com.github.lemniscate.stack.boot.model.UserDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Component
public class UserController extends ApiResourceController<Long, UserAccount, UserAccount, UserAccount, UserAccount> {

    @Inject
    private ApiResourceService<Long, UserDevice, UserDevice, UserDevice, UserDevice> deviceService;

    @Inject
    private ApiResourceAssembler<Long, UserDevice, UserDevice, UserDevice, UserDevice> deviceAssembler;

    @AssembleWith("devices")
    @RequestMapping(value="/{parentId}/devices", method= RequestMethod.GET)
    public ResponseEntity<Page<Resource<UserDevice>>> getPets(@PathVariable Long parentId){
        UserAccount parent = service.findOne(parentId);
        Assert.notNull(parent, "Couldn't locate the user");

        List<UserDevice> entities = parent.getDevices();
        List<Resource<UserDevice>> resources = deviceAssembler.toResources(entities);
        Page<Resource<UserDevice>> pagedResources = new PageImpl<Resource<UserDevice>>(resources);
        ResponseEntity<Page<Resource<UserDevice>>> response = new ResponseEntity<Page<Resource<UserDevice>>>(pagedResources, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value="/{parentId}/devices", method= RequestMethod.POST)
    public ResponseEntity<Resource<UserDevice>> postPets(@PathVariable Long parentId, @RequestBody UserDevice device){
        UserAccount parent = service.findOne(parentId);
        Assert.notNull(parent, "Couldn't locate the user");

        device.setOwner(parent);
        UserDevice entity = deviceService.createForRead(device);

        Resource<UserDevice> resource = deviceAssembler.toResource(entity);
        ResponseEntity<Resource<UserDevice>> response = new ResponseEntity<Resource<UserDevice>>(resource, HttpStatus.OK);
        return response;
    }

}
