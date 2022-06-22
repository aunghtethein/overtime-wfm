package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ot.model.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}
