package com.example.user.service;

import com.example.user.dao.UserDao;
import com.example.user.dao.UserDaoImpl;
import com.example.user.entity.User;
import com.example.user.util.HibernateUtil;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
    }

    // Валидация
    private void validateUser(String name, String email, int age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            throw new IllegalArgumentException("Некорректный формат email");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Возраст должен быть от 0 до 150");
        }
    }

    public User createUser(String name, String email, int age) {
        validateUser(name, email, age);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        return userDao.save(user);
    }

    public User findUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        return userDao.findById(id);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public User updateUser(Long id, String name, String email, int age) {
        validateUser(name, email, age);
        User existing = findUserById(id);
        if (existing == null) {
            throw new RuntimeException("Пользователь с ID " + id + " не найден");
        }
        existing.setName(name);
        existing.setEmail(email);
        existing.setAge(age);
        return userDao.save(existing);
    }

    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        userDao.delete(id);
    }
}