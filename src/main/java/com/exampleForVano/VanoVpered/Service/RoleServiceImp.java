package com.exampleForVano.VanoVpered.Service;

import com.exampleForVano.VanoVpered.model.Role;
import com.exampleForVano.VanoVpered.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
}
