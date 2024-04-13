package com.project.evm.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.evm.models.User;

public interface UserRepository extends CrudRepository<User,Integer>{
}
