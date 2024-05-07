package com.virtualbank.repository;

import com.virtualbank.model.AccountManager;

import java.io.*;

public class AccountManagerSerializer {

    // 静态成员变量，存储目录路径
    public static final String DIRECTORY_PATH = "src/main/resources/account_manager";

    // 序列化AccountManager对象
    public static void serializeAccountManager(AccountManager accountManager, String username) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // 如果目录不存在，创建目录
        }
        String filePath = DIRECTORY_PATH + File.separator + username;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(accountManager);
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 反序列化AccountManager对象
    public static AccountManager deserializeAccountManager(String username) throws IOException, ClassNotFoundException {
        String filePath = DIRECTORY_PATH + File.separator + username;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found for username: " + username);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (AccountManager) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            throw e; // 重新抛出异常，让调用者处理
        }
    }
}
