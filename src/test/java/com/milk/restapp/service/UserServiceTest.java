package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.model.User;
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
public class UserServiceTest {

    @Mock
    private UserDAOImpl userDAO;

    @InjectMocks
    private UserService userService;

    private User getUser(){
        return new User(1, "John Doe");
    }

    private List<User> getUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(1, "John Doe"));
        users.add(new User(2, "Mike Snow"));
        return users;
    }

    @Test
    public void testGetAllUsers(){
        when(userDAO.getAll()).thenReturn(Lists.newArrayList(getUsers()));
        assertEquals(2, userService.findAll().size());
        assertEquals("John Doe", userService.findAll().get(0).getName());
        assertEquals("Mike Snow", userService.findAll().get(1).getName());

        verify(userDAO, times(3)).getAll();
    }

    @Test
    public void testGetUserById(){
        when(userDAO.getById(1)).thenReturn(getUser());
        assertEquals(1, (int) userService.findById(1).getId());
        assertEquals("John Doe", userService.findById(1).getName());

        verify(userDAO, times(2)).getById(1);
    }

    @Test
    public void testSaveUser(){
        when(userDAO.save(getUser())).thenReturn(getUser());
        assertEquals(1, userService.save(getUser()).getId());
        assertEquals("John Doe", userService.save(getUser()).getName());

        verify(userDAO, times(2)).save(getUser());
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.update(getUser())).thenReturn(new User(1, "John Bizz"));
        assertEquals("John Bizz", userService.update(getUser()).getName());
        assertEquals(1, userService.update(getUser()).getId());

        verify(userDAO, times(2)).update(getUser());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userDAO).deleteById(1);
        userService.deleteById(1);
        verify(userDAO).deleteById(1);
    }

}
