package com.glos.databaseAPIService.domain.repository;


import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long>
{
    @Query(value = """
            SELECT file FROM File file
            WHERE file.repository.id = :repositoryId
            """)
    List<File> findAllByRepositoryId(@Param("repositoryId") Long repositoryId);

    @Query("""
         SELECT file FROM File file
         WHERE :#{#filter.id} IS NULL OR file.id = :#{#filter.id}
         AND (:#{#filter.rootPath} IS NULL OR file.rootPath = :#{#filter.rootPath})
         AND (:#{#filter.rootFilename} IS NULL OR file.rootFilename = :#{#filter.rootFilename})
         AND (:#{#filter.rootFullName} IS NULL OR file.rootFullName = :#{#filter.rootFullName})
         AND (:#{#filter.rootSize} IS NULL OR file.rootSize = :#{#filter.rootSize})
         AND (:#{#filter.rootFormat} IS NULL OR file.rootFormat = :#{#filter.rootFormat})
         AND (:#{#filter.displayPath} IS NULL OR file.displayPath = :#{#filter.displayPath})
         AND (:#{#filter.displayFilename} IS NULL OR file.displayFilename = :#{#filter.displayFilename})
         AND (:#{#filter.displayFullName} IS NULL OR file.displayFullName = :#{#filter.displayFullName})
         AND (:#{#filter.repository} IS NULL OR file.repository = :#{#filter.repository})
        """)
    List<File> findAllByFilter(@Param("filter") FileFilter filter);

    @Query(value = """
            SELECT file FROM File file
            WHERE file.rootFullName = :rootFullName
            """)
    Optional<File> findByRootFullName(@Param("rootFullName") String rootFullName);
}
