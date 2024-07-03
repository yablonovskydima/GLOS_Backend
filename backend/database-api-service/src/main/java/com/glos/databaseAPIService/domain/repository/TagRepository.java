package com.glos.databaseAPIService.domain.repository;


import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>
{
    Optional<Tag> findByName(String name);
}
