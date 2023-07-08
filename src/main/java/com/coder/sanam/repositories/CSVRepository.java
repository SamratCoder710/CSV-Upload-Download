package com.coder.sanam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.sanam.models.Data;

public interface CSVRepository extends JpaRepository<Data, Long> {
	
	Optional<Data> findByDescription(String description);

}
