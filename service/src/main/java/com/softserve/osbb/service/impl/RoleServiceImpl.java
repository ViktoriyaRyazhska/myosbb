package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Role;
import com.softserve.osbb.repository.RoleRepository;
import com.softserve.osbb.service.RoleService;
import com.softserve.osbb.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roma on 13/07/2016.
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Role addRole(Role role) {
        if(role == null) {
            return null;
        }
        return roleRepository.saveAndFlush(role);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Role getRole(Integer id) {
        return roleRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Role getRole(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long countRole() {
        return roleRepository.count();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsRole(Integer id) {
        return roleRepository.exists(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Role updateRole(Role role) {
        if(roleRepository.exists(role.getRoleId())) {
            return roleRepository.save(role);
        } else {
            throw new IllegalArgumentException("Role with id=" + role.getRoleId()
                    + " doesn't exist. First try to add this role.");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteRole(Integer id) {
        roleRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllRole() {
        roleRepository.deleteAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Role> getAllRole(Integer pageNumber, String sortBy, Boolean order) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, Constants.DEF_ROWS,
                getSortingOrder(order), sortBy == null ? "name" : sortBy);
        return roleRepository.findAll(pageRequest);
    }

    public Sort.Direction getSortingOrder(Boolean order) {
        if (order == null) {
            return Sort.Direction.DESC;
        }
        return order == true ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
