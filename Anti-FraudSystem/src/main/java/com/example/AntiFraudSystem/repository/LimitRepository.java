package com.example.AntiFraudSystem.repository;

import com.example.AntiFraudSystem.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
}
