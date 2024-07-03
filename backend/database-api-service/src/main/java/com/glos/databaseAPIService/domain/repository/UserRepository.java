package com.glos.databaseAPIService.domain.repository;


import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
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
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query(value = """
            SELECT user FROM User user
            WHERE user.phone_number = :phoneNumber
            """)
    Optional<User> findByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    @Query(value = """
            SELECT user FROM User user
            WHERE :#{#filter.id} IS NULL OR user.id = :#{#filter.id}
                AND :#{#filter.username} IS NULL OR user.username = :#{#filter.username}
                AND :#{#filter.email} IS NULL OR user.email = :#{#filter.email}
                AND :#{#filter.phone_number} IS NULL OR user.phone_number = :#{#filter.phone_number}
                AND :#{#filter.gender} IS NULL OR user.gender = :#{#filter.gender}
                AND :#{#filter.first_name} IS NULL OR user.first_name = :#{#filter.first_name}
                AND :#{#filter.last_name} IS NULL OR user.last_name = :#{#filter.last_name}
                AND :#{#filter.birthdate} IS NULL OR user.birthdate = :#{#filter.birthdate}
                AND :#{#filter.is_account_non_expired} IS NULL OR user.is_account_non_expired = :#{#filter.is_account_non_expired}
                AND :#{#filter.is_account_non_locked} IS NULL OR user.is_account_non_locked = :#{#filter.is_account_non_locked}
                AND :#{#filter.is_credentials_non_expired} IS NULL OR user.is_credentials_non_expired = :#{#filter.is_credentials_non_expired}
                AND :#{#filter.is_enabled} IS NULL OR user.is_enabled = :#{#filter.is_enabled}
                AND :#{#filter.is_deleted} IS NULL OR user.is_deleted = :#{#filter.is_deleted}
            """)
    public List<User> findAllByFilter(@Param("filter") EntityFilter filter);

}
