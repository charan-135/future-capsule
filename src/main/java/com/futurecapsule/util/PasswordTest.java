package com.futurecapsule.util;

public class PasswordTest {

    public static void main(String[] args) {

        String password = "MySecret123";

        String hashedPassword =
                PasswordUtil.hashPassword(password);

        System.out.println("Original password:");
        System.out.println(password);

        System.out.println("\nHashed password:");
        System.out.println(hashedPassword);

        boolean correct =
                PasswordUtil.checkPassword(
                        "MySecret123",
                        hashedPassword
                );

        boolean incorrect =
                PasswordUtil.checkPassword(
                        "WrongPassword",
                        hashedPassword
                );

        System.out.println("\nCorrect password: " + correct);
        System.out.println("Wrong password: " + incorrect);
    }
}