package com.exampleForVano.PP3_1_4.Service;

import com.exampleForVano.PP3_1_4.model.Role;
import com.exampleForVano.PP3_1_4.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
    @Transactional
    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name);
    }
    @Transactional
    public Set<Role> getSetOfRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(getRoleByName(role));
        }
        return roleSet;
    }
    @Transactional
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }
}
