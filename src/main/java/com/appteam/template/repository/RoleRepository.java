package com.appteam.template.repository;

import com.appteam.template.data.Order;
import com.appteam.template.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
