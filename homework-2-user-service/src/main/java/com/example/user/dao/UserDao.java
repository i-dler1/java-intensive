package com.example.user.dao;

import com.example.user.entity.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
}