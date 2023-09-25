package cypherf.demo.springcrud.service;

import cypherf.demo.springcrud.Application;
import cypherf.demo.springcrud.model.Message;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test
    void testCreateValid() {
        final String text = "Test case for a valid create call";
        final Message created = messageService.create(text);

        assertEquals(text, created.getText());
    }

    @Test
    void testCreateDuplicate() {
        final String text = "Test case for a duplicate create call";
        messageService.create(text);
        final Throwable thrown = assertThrows(EntityExistsException.class, () -> {
            messageService.create(text);
        });

        assertEquals(String.format("Message with text '%s' already exists", text), thrown.getMessage());
    }

    @Test
    void testReadByIdExists() {
        final Message created = messageService.create("Test case for a read by id - existing");
        assertTrue(messageService.readById(created.getId()).isPresent());
    }

    @Test
    void testReadByIdNotExists() {
        assertTrue(messageService.readById(0L).isEmpty());
    }

    @Test
    void testReadByTextExists() {
        final String text = "Test case for a read by text - exists";
        messageService.create(text);
        assertTrue(messageService.readByText(text).isPresent());
    }

    @Test
    void testReadByTextNotExists() {
        final String text = "Test case for a read by text - not exists";
        assertTrue(messageService.readByText(text).isEmpty());
    }

    @Test
    void testReadAll() {
        final String base = "Test case for read all - message ";
        final List<String> created = new ArrayList<>();
        for (int i = 1 ; i <= 3 ; i++) {
            created.add(messageService.create(base + i).getText());
        }

        final List<String> read = new ArrayList<>();
        messageService.readAll().forEach(m -> {
                read.add(m.getText());
        });

        created.forEach(s -> {
            assertTrue(read.contains(s));
        });
    }

    @Test
    void testUpdateExists() {
        final String initialString = "Test case for update - exists - initial message";
        final String updatedString = "Test case for update - exists - updated message";
        final Message initial = messageService.create(initialString);
        initial.setText(updatedString);
        messageService.updateMessage(initial);

        assertTrue(messageService.readByText(initialString).isEmpty());
        assertTrue(messageService.readByText(updatedString).isPresent());
    }

    @Test
    void testUpdateNotExists() {
        final Message notPersistedYet = new Message("Test case for update - not exists");
        final Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            messageService.updateMessage(notPersistedYet);
        });
        assertEquals("Message with id '0' doesn't exist", thrown.getMessage());
    }
}