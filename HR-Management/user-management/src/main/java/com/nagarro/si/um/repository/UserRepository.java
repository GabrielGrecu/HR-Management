package com.nagarro.si.um.repository;

import com.nagarro.si.um.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
