package com.example.service;

public interface FileService {
    void delete(String absolutePath);

    void move(String file, String directory);

    String newDirectory(String directory);

    boolean rename(String oldName, String newName);
}
