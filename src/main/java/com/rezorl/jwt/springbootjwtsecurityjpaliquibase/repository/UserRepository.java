package com.rezorl.jwt.springbootjwtsecurityjpaliquibase.repository;

import javax.transaction.Transactional;
import com.rezorl.jwt.springbootjwtsecurityjpaliquibase.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}