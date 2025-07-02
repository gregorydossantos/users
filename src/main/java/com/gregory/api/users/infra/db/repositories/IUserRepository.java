package com.gregory.api.users.infra.db.repositories;

import com.gregory.api.users.infra.db.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<Users, UUID>, PagingAndSortingRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);
}