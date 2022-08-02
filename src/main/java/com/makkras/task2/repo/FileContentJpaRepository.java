package com.makkras.task2.repo;

import com.makkras.task2.entity.FileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileContentJpaRepository extends JpaRepository<FileContent,String> {
    boolean existsByFileName(String fileName);
    @Query("SELECT content.fileName FROM FileContent content")
    List<String> findAllFileName();
}
