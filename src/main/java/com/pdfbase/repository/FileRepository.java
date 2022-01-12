package com.pdfbase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pdfbase.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>{

	@Query("from File f where f.name like %:name%")
	List<File> findAllByNameLike(String name);

	@Query("Select path from File f where f.id = :id")
	String findFilePathById(Long id);

}
