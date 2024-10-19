package com.icia.recipe.home.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordManager {

    // 비밀번호 확인 함수
    public boolean checkPassword(String storedPasswordHash, String providedPassword) {
        return BCrypt.checkpw(providedPassword, storedPasswordHash);
    }

    // 비밀번호 해시 함수
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 비밀번호 변경 함수
    public String changePassword(String storedPasswordHash, String currentPassword, String newPassword) {
        if (checkPassword(storedPasswordHash, currentPassword)) {
            // 비밀번호가 일치하면 새 비밀번호로 변경
            return hashPassword(newPassword);
        } else {
            // 비밀번호가 일치하지 않으면 null 반환
            return null;
        }
    }

    public void main(String[] args) {
        // 기존 저장된 비밀번호 해시 (예: "1111"의 해시값)
//        String storedPasswordHash = hashPassword("1111");
//        String currentPassword = "1111";
//        String newPassword = "1234";

//        String newPasswordHash = changePassword(storedPasswordHash, currentPassword, newPassword);
//
//        if (newPasswordHash != null) {
//            System.out.println("비밀번호가 성공적으로 변경되었습니다. 새로운 해시: " + newPasswordHash);
//        } else {
//            System.out.println("현재 비밀번호가 일치하지 않습니다.");
//        }
    }
}