package com.tangerine.productmng.model.user.dao;

import com.tangerine.productmng.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByUsername(String username);

}
