package com.project.evm.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.evm.models.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Integer>{

    @Modifying
    @Query("DELETE FROM users WHERE name = :username")
    public void deleteUserByUsername(String username);

    @Query("SELECT COUNT(*) FROM users AS user_count")
    public long getCount();

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE name = :username)")
    public boolean existsByUsername(String username);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    public boolean existsByEmail(String email);
}
