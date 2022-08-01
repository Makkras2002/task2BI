package com.makkras.task2.repo;

import com.makkras.task2.entity.FileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileContentJpaRepository extends JpaRepository<FileContent,String> {
    boolean existsByFileName(String fileName);
}
