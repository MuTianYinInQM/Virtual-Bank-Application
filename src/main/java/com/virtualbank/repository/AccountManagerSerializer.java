package com.virtualbank.repository;

import com.virtualbank.model.AccountManager;

import java.io.*;
/**
 * Utility class for serializing and deserializing AccountManager objects.
 */
public class AccountManagerSerializer {

    // 静态成员变量，存储目录路径
    /**
     * The directory path where AccountManager objects will be serialized.
     */
    public static final String DIRECTORY_PATH = "src/main/resources/account_manager";

    // 序列化AccountManager对象

    /**
     * Serializes an AccountManager object to a file.
     *
     * @param accountManager The AccountManager object to serialize.
     * @param username       The username associated with the AccountManager.
     */
    public static void serializeAccountManager(AccountManager accountManager, String username) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // 如果目录不存在，创建目录
        }
        String filePath = DIRECTORY_PATH + File.separator + username;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath, false))) {
            out.writeObject(accountManager);
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // 反序列化AccountManager对象
    /**
     * Deserializes an AccountManager object from a file.
     *
     * @param username The username associated with the AccountManager to deserialize.
     * @return The deserialized AccountManager object.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public static AccountManager deserializeAccountManager(String username) throws IOException, ClassNotFoundException {
        String filePath = DIRECTORY_PATH + File.separator + username;
        File file = new File(filePath);
        if (!file.exists()) {
            return new AccountManager();
//            throw new FileNotFoundException("File not found for username: " + username);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (AccountManager) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            throw e; // 重新抛出异常，让调用者处理
        }
    }
}
