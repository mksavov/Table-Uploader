package com.example.application.dao;

import com.example.application.model.database.UploadedFileLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableUploadLogsRepository extends JpaRepository<UploadedFileLogs, Integer> {
}
