package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.Role;
import com.tup.ps.erpevents.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName rolename);
}
