package com.tes.buana.repository;

import com.tes.buana.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndMarkForDeleteIsFalse(String name);
    Optional<Role> findByIdAndMarkForDeleteIsFalse(String id);
    Page<Role> findAllByMarkForDeleteIsFalse(Pageable pageable);
}