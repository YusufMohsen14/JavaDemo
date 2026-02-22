package com.Java.demo.repository;

import com.Java.demo.model.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long> {
    Optional<UserContact> findByUserId(Long userId);
}
