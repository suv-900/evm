package com.project.host.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.host.models.HostEntity;

public interface HostRepository extends CrudRepository<HostEntity,Long>{

    @Modifying
    @Query("DELETE FROM hosts WHERE name = :username")
    public void deleteByUsername(String username);

    @Query("SELECT COUNT(*) FROM hosts AS host_count")
    public long getCount();

    @Query("SELECT EXISTS(SELECT 1 FROM hosts WHERE name = :username) AS host_exists")
    public boolean existsByUsername(String username);

    @Query("SELECT EXISTS(SELECT 1 FROM hosts WHERE email = :email) AS host_exists")
    public boolean existsByEmail(String email);

}
