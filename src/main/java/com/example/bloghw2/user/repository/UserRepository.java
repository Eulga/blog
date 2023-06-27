package com.example.bloghw2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloghw2.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
