package com.tunisiecables.smartitplatform.repository;

import com.tunisiecables.smartitplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}