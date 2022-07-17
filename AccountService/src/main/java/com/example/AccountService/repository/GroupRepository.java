package com.example.AccountService.repository;

import com.example.AccountService.entity.Group;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

    Group findByCode(String code);
}
