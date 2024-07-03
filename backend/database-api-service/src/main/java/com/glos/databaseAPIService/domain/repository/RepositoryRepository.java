package com.glos.databaseAPIService.domain.repository;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@org.springframework.stereotype.Repository
public interface RepositoryRepository extends JpaRepository<Repository, Long>
{
    @Query(value = """
               SELECT r FROM Repository r
               WHERE :#{#filter.id} IS NULL OR r.id = :#{#filter.id}
                 AND :#{#filter.rootPath} IS NULL OR r.rootPath = :#{#filter.rootPath}
                 AND :#{#filter.rootName} IS NULL OR r.rootName = :#{#filter.rootName}
                 AND :#{#filter.rootFullName} IS NULL OR r.rootFullName = :#{#filter.rootFullName}
                 AND :#{#filter.isDefault} IS NULL OR r.isDefault = :#{#filter.isDefault}
                 AND :#{#filter.displayPath} IS NULL OR r.displayPath = :#{#filter.displayPath}
                 AND :#{#filter.displayName} IS NULL OR r.displayName = :#{#filter.displayName}
                 AND :#{#filter.displayFullName} IS NULL OR r.displayFullName = :#{#filter.displayFullName}
                 AND :#{#filter.description} IS NULL OR r.description = :#{#filter.description}
                 AND :#{#filter.owner} IS NULL OR r.owner = :#{#filter.owner}
               """)
    public List<Repository> findAllByFilter(@Param("filter") RepositoryFilter filter);

    @Query(value = """
               SELECT repository FROM Repository repository
               WHERE repository.owner.id = :id
               """)
    List<Repository> findByOwnerId(@Param("id") Long id);

    @Query(value = """
               SELECT repository FROM Repository repository
               WHERE repository.rootFullName = :rootFullName
               """)
    Optional<Repository> findByRootFullName(@Param("rootFullName") String rootFullName);
}

