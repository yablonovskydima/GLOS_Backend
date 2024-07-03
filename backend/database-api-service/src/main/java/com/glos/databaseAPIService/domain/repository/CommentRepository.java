package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entities.Comment;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 	@author - yablonovskydima
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{

//    @Query(value = """
//            SELECT c FROM Comment c
//            WHERE c IN (SELECT com FROM File f JOIN f.comments com WHERE f.rootFullName = :rootFullName)
//            OR c IN (SELECT com FROM Repository r JOIN r.comments com WHERE r.rootFullName = :rootFullName)
//            """)
//    Page<Comment> findCommentsByRootFullName(@Param("rootFullName") String rootFullName, Pageable pageable);

    Page<Comment> findAllByResourcePath(@Param("resourcePath") String resourcePath, Pageable pageable);


}
