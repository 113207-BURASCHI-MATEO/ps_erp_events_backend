package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);
}
