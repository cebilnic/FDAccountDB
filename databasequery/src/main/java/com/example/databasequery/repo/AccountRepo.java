package com.example.databasequery.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.databasequery.model.AccountInfo;

@Repository
public interface AccountRepo extends JpaRepository<AccountInfo, Long> {
}
