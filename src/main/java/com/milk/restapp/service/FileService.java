package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.FileDAOImpl;
import com.milk.restapp.model.File;

import java.util.List;

/**
 * @author Jack Milk
 */
public class FileService {

    private FileDAOImpl fileDAO;

    public FileService(FileDAOImpl fileDAO) {
        this.fileDAO = fileDAO;
    }

    public List<File> findAll() {
        return fileDAO.getAll();
    }

    public List<File> findFilesByUserId(Integer id) {
        return fileDAO.getFilesByUserId(id);
    }

    public File findById(Integer id) {
        return fileDAO.getById(id);
    }

    public File save(File file) {
        return fileDAO.save(file);
    }

    public File update(File file) {
        return fileDAO.update(file);
    }

    public void deleteById(Integer id) {
        fileDAO.deleteById(id);
    }
}
