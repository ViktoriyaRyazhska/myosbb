/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import com.softserve.osbb.model.Role;
import com.softserve.osbb.service.RoleService;
import com.softserve.osbb.util.paging.PageDataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Roma on 13/07/2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/role")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Role>>> getAllRole() {
        logger.info("Get all role: ");
        List<Role> roleList = roleService.getAllRole();
        final List<Resource<Role>> resourceRoleList = new ArrayList<>();
        
        for(Role o: roleList) {
            resourceRoleList.add(addResourceLinkToRole(o));
        }
        
        return new ResponseEntity<>(resourceRoleList, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Role>>> getAllRole(
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder) {
        logger.info("get all roles by page number: " + pageNumber);
        Page<Role> rolesByPage = roleService.getAllRole(pageNumber, sortedBy, ascOrder);

        int currentPage = rolesByPage.getNumber() + 1;
        int begin = Math.max(1, currentPage - 5);
        int totalPages = rolesByPage.getTotalPages();
        int end = Math.min(currentPage + 5, totalPages);

        List<Resource<Role>> resourceList = new ArrayList<>();
        rolesByPage.forEach((role) -> resourceList.add(getLink(toResource(role))));

        PageDataObject<Resource<Role>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(Integer.valueOf(currentPage).toString());
        pageDataObject.setBeginPage(Integer.valueOf(begin).toString());
        pageDataObject.setEndPage(Integer.valueOf(end).toString());
        pageDataObject.setTotalPages(Integer.valueOf(totalPages).toString());

        return new ResponseEntity<>(pageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Resource<Role>> createRole(@RequestBody Role role) {
        logger.info("Create role.  " + role);
        role = roleService.addRole(role);
        return new ResponseEntity<>(addResourceLinkToRole(role), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Role>> getRoleById(@PathVariable("id") Integer roleId) {
        logger.info("Get one role by id: " + roleId);
        Role role = roleService.getRole(roleId);
        return new ResponseEntity<>(addResourceLinkToRole(role), HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Role>> getRoleByName(@PathVariable("name") String name) {
        logger.info("Get one role by name: " + name);
        Role role = roleService.getRole(name);
        return new ResponseEntity<>(addResourceLinkToRole(role), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Role>> updateRole(@RequestBody Role role) {
        logger.info("Update role with id: " + role.getRoleId());
        Role updatedRole = roleService.updateRole(role);
        return new ResponseEntity<>(addResourceLinkToRole(updatedRole), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Role>> deleteRole(@PathVariable("id") Integer id) {
        logger.info("Delete role with id: " + id );
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Role>  deleteAllOsbb() {
        logger.info("Delete all role.");
        roleService.deleteAllRole();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private Resource<Role> addResourceLinkToRole(Role role) {
        if (role == null) {
            return null;
        }
        
        Resource<Role> roleResource = new Resource<>(role);
        
        roleResource.add(linkTo(methodOn(RoleController.class)
                .getRoleById(role.getRoleId()))
                .withSelfRel());
        
        return roleResource;
    }

    private Resource<Role> getLink(Resource<Role> roleResource) {
        //adding self-link
        roleResource.add(linkTo(methodOn(RoleController.class)
                .getRoleById(roleResource.getContent().getRoleId()))
                .withSelfRel());
        return roleResource;
    }

}
