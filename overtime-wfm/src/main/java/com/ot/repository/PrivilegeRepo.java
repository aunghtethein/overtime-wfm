package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ot.model.Privilege;
@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, Integer>{

}
