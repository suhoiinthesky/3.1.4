package com.exampleForVano.PP3_1_4.repository;


import com.exampleForVano.PP3_1_4.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String name);

}
