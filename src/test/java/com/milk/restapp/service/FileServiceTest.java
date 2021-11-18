package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.FileDAOImpl;
import com.milk.restapp.model.File;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Jack Milk
 */

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @Mock
    private FileDAOImpl fileDAO;

    @InjectMocks
    private FileService fileService;

    public File getFile() {
        return new File(1, "newimage.jpg", "/path/to/file");
    }

    public List<File> getFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File(1, "newimage.jpg", "/path/to/file"));
        files.add(new File(2, "textfile.txt", "/path/to/textfile"));
        return files;
    }

    @Test
    public void testGetAllFiles() {
        when(fileDAO.getAll()).thenReturn(Lists.newArrayList(getFiles()));
        assertEquals(2, fileService.findAll().size());
        assertEquals("newimage.jpg", fileService.findAll().get(0).getName());
        assertEquals("/path/to/file", fileService.findAll().get(0).getPath());
        assertEquals("textfile.txt", fileService.findAll().get(1).getName());
        assertEquals("/path/to/textfile", fileService.findAll().get(1).getPath());

        verify(fileDAO, times(5)).getAll();
    }

    @Test
    public void testGetFileById() {
        when(fileDAO.getById(1)).thenReturn(getFile());
        assertEquals(1, (int) fileService.findById(1).getId());
        assertEquals("newimage.jpg", fileService.findById(1).getName());
        assertEquals("/path/to/file", fileService.findById(1).getPath());

        verify(fileDAO, times(3)).getById(1);
    }

    @Test
    public void testGetFilesByUserId() {
        when(fileDAO.getFilesByUserId(1)).thenReturn(getFiles());
        assertEquals(2, fileService.findFilesByUserId(1).size());
        assertEquals("newimage.jpg", fileService.findFilesByUserId(1).get(0).getName());
        assertEquals("/path/to/file", fileService.findFilesByUserId(1).get(0).getPath());
        assertEquals("textfile.txt", fileService.findFilesByUserId(1).get(1).getName());
        assertEquals("/path/to/textfile", fileService.findFilesByUserId(1).get(1).getPath());

        verify(fileDAO, times(5)).getFilesByUserId(1);
    }

    @Test
    public void testFileSave(){
        when(fileDAO.save(getFile())).thenReturn(getFile());
        assertEquals(1, fileService.save(getFile()).getId());
        assertEquals("newimage.jpg", fileService.save(getFile()).getName());
        assertEquals("/path/to/file", fileService.save(getFile()).getPath());

        verify(fileDAO, times(3)).save(getFile());
    }

    @Test
    public void testUpdateFile(){
        when(fileDAO.update(getFile())).thenReturn(new File(1, "foto.jpg", "/path/to/image"));
        assertEquals(1, fileService.update(getFile()).getId());
        assertEquals("foto.jpg", fileService.update(getFile()).getName());
        assertEquals("/path/to/image", fileService.update(getFile()).getPath());

        verify(fileDAO, times(3)).update(getFile());
    }

    @Test
    public void testDeleteFileById(){
        doNothing().when(fileDAO).deleteById(1);
        fileService.deleteById(1);
        verify(fileDAO).deleteById(1);
    }

}
