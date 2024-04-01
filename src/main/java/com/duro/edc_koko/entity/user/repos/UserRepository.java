package com.duro.edc_koko.entity.user.repos;

import com.duro.edc_koko.entity.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
