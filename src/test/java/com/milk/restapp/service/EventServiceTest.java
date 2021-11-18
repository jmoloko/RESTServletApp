package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.EventDAOImpl;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.model.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Jack Milk
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventDAOImpl eventDAO;

    @InjectMocks
    private EventService eventService;

    private User getUser(){
        return new User(1, "John Doe");
    }


    public List<File> getFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File(1, "newimage.jpg", "/path/to/file"));
        files.add(new File(2, "textfile.txt", "/path/to/textfile"));
        return files;
    }

    public Event getEvent() {
        return new Event(1, getUser(), getFiles().get(0), Occasion.UPLOAD, LocalDateTime.now());
    }


    public Event getNewEvent() {
        return new Event(1, getUser(), getFiles().get(0), Occasion.RENAME, LocalDateTime.now());
    }

    public Event event() {
        return new Event(getUser(), getFiles().get(0), Occasion.UPLOAD);
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, getUser(), getFiles().get(0), Occasion.UPLOAD, LocalDateTime.now()));
        events.add(new Event(2, getUser(), getFiles().get(1), Occasion.DOWNLOAD, LocalDateTime.now()));
        return events;
    }

    @Test
    public void testGetAllEvents() {
        when(eventDAO.getAll()).thenReturn(Lists.newArrayList(getEvents()));
        assertEquals(2, eventService.findAll().size());
        assertEquals("John Doe", eventService.findAll().get(0).getUser().getName());
        assertEquals("John Doe", eventService.findAll().get(1).getUser().getName());
        assertEquals("newimage.jpg", eventService.findAll().get(0).getFile().getName());
        assertEquals("/path/to/file", eventService.findAll().get(0).getFile().getPath());
        assertEquals("textfile.txt", eventService.findAll().get(1).getFile().getName());
        assertEquals("/path/to/textfile", eventService.findAll().get(1).getFile().getPath());
        assertEquals(Occasion.UPLOAD, eventService.findAll().get(0).getOccasion());
        assertEquals(Occasion.DOWNLOAD, eventService.findAll().get(1).getOccasion());

        verify(eventDAO, times(9)).getAll();
    }

    @Test
    public void testGetEventById(){
        when(eventDAO.getById(1)).thenReturn(getEvent());
        assertEquals(1, eventService.save(1).getId());
        assertEquals("John Doe", eventService.save(1).getUser().getName());
        assertEquals("newimage.jpg", eventService.save(1).getFile().getName());
        assertEquals("/path/to/file", eventService.save(1).getFile().getPath());
        assertEquals(Occasion.UPLOAD, eventService.save(1).getOccasion());

        verify(eventDAO, times(5)).getById(1);
    }

    @Test
    public void testGetEventsByUserId() {
        when(eventDAO.getEventsByUserId(1)).thenReturn(getEvents());
        assertEquals(2, eventService.findEventByUserId(1).size());
        assertEquals(1, eventService.findEventByUserId(1).get(0).getUser().getId());
        assertEquals(1, eventService.findEventByUserId(1).get(1).getUser().getId());
        assertEquals("John Doe", eventService.findEventByUserId(1).get(0).getUser().getName());
        assertEquals("newimage.jpg", eventService.findEventByUserId(1).get(0).getFile().getName());
        assertEquals("/path/to/file", eventService.findEventByUserId(1).get(0).getFile().getPath());
        assertEquals(Occasion.DOWNLOAD, eventService.findEventByUserId(1).get(1).getOccasion());

        verify(eventDAO, times(7)).getEventsByUserId(1);
    }

    @Test
    public void testSaveEvent() {
        when(eventDAO.save(event())).thenReturn(event());
        assertEquals("John Doe", eventService.save(event()).getUser().getName());
        assertEquals("newimage.jpg", eventService.save(event()).getFile().getName());
        assertEquals("/path/to/file", eventService.save(event()).getFile().getPath());
        assertEquals(Occasion.UPLOAD, eventService.save(event()).getOccasion());

        verify(eventDAO, times(4)).save(event());
    }

    @Test
    public void testUpdateEvent() {
        when(eventDAO.update(event())).thenReturn(new Event(getUser(), getFiles().get(0), Occasion.RENAME));
        assertEquals("John Doe", eventService.update(event()).getUser().getName());
        assertEquals("newimage.jpg", eventService.update(event()).getFile().getName());
        assertEquals("/path/to/file", eventService.update(event()).getFile().getPath());
        assertEquals(Occasion.RENAME, eventService.update(event()).getOccasion());

        verify(eventDAO, times(4)).update(event());
    }

    @Test
    public void testDeleteEventById(){
        doNothing().when(eventDAO).deleteById(1);
        eventService.deleteById(1);
        verify(eventDAO).deleteById(1);
    }

}
