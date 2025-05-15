package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>,
        JpaSpecificationExecutor<TemplateEntity> {
}
