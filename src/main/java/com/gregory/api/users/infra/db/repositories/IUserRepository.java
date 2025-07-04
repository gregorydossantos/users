package com.gregory.api.users.infra.db.repositories;

import com.gregory.api.users.infra.db.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUserId(String userId);
}