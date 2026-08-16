package com.ecom.authservice.repository;

import com.ecom.authservice.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<@NonNull User, @NonNull  UUID> {
    Optional<User> findByUsername(String username);
}
