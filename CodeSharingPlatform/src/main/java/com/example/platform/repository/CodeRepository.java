package com.example.platform.repository;

import com.example.platform.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    @Query("SELECT c FROM Code c WHERE c.uuid = ?1")
    Optional<Code> findByUUID(String uuid);
}
