package com.Java.demo.repository;

import com.Java.demo.model.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserContactRepository extends JpaRepository<UserContact, Long> {
    Optional<UserContact> findByUserId(Long userId);
}
