package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long>
{
    @Query(value = """
            SELECT group FROM Group group
            WHERE :#{#filter.asMap().id} IS NULL OR group.id = :#{#filter.asMap().get("id")}
            AND :#{#filter.asMap().owner} IS NULL OR group.owner = :#{#filter.asMap().get("owner")}
            AND (:#{#filter.asMap().users} IS NULL OR ARRAY_INTERSECT(group.users, :#{#filter.asMap().get("users")}) IS NOT NULL)
            """)
    List<Group> findAllByFilter(@Param("filter") EntityFilter filter);
}
