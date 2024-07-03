package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entities.Role;
import com.glos.databaseAPIService.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    default Map.Entry<Role, Boolean> ensureByName(String name) {
        final Optional<Role> tagOpt = findByName(name);
        return Map.entry(tagOpt.orElseGet(() -> save(new Role(null, name))), tagOpt.isEmpty());
    }

    Optional<Role> findByName(String name);
}
