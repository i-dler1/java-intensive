package com.example.user;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import com.example.user.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== USER SERVICE ===");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Выйти");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Имя: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Возраст: ");
                        int age = Integer.parseInt(scanner.nextLine());
                        User created = service.createUser(name, email, age);
                        System.out.println("Создан пользователь: " + created);
                        break;

                    case "2":
                        System.out.print("Введите ID: ");
                        Long findId = Long.parseLong(scanner.nextLine());
                        User found = service.findUserById(findId);
                        System.out.println(found != null ? found : "Пользователь не найден");
                        break;

                    case "3":
                        List<User> users = service.findAllUsers();
                        if (users.isEmpty()) {
                            System.out.println("Пользователей нет.");
                        } else {
                            users.forEach(System.out::println);
                        }
                        break;

                    case "4":
                        System.out.print("Введите ID для обновления: ");
                        Long updId = Long.parseLong(scanner.nextLine());

                        User existing = service.findUserById(updId);
                        if (existing == null) {
                            System.out.println("Пользователь с ID " + updId + " не найден");
                            break;
                        }

                        System.out.print("Новое имя: ");
                        String newName = scanner.nextLine();
                        if (newName.isEmpty()) newName = existing.getName();

                        System.out.print("Новый email: ");
                        String newEmail = scanner.nextLine();
                        if (newEmail.isEmpty()) newEmail = existing.getEmail();

                        System.out.print("Новый возраст: ");
                        String ageInput = scanner.nextLine();
                        int newAge = ageInput.isEmpty() ? existing.getAge() : Integer.parseInt(ageInput);

                        User updated = service.updateUser(updId, newName, newEmail, newAge);
                        System.out.println("Обновленный пользователь: " + updated);
                        break;

                    case "5":
                        System.out.print("Введите ID для удаления: ");
                        Long delId = Long.parseLong(scanner.nextLine());
                        service.deleteUser(delId);
                        System.out.println("Пользователь удален.");
                        break;

                    case "6":
                        System.out.println("Выход...");
                        HibernateUtil.shutdown();
                        scanner.close();
                        return;

                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число для ID или возраста");
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }
}