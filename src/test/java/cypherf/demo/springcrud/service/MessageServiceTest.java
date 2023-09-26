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

import java.util.Arrays;
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
        final String base = "Test case for read all - message #";
        final List<String> created = Arrays.asList(base + 1, base + 2, base + 3);
        created.forEach(messageService::create);

        // Mapping List<Message> to List<String> containing message ids
        final List<String> read = messageService
                .readAll()
                .stream()
                .map(Message::getText)
                .toList();

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
        final Message updated = messageService.update(initial);

        assertTrue(messageService.readByText(initialString).isEmpty());
        assertTrue(messageService.readByText(updatedString).isPresent());
        assertEquals(initial.getId(), updated.getId());
    }

    @Test
    void testUpdateNotExists() {
        final Message notPersistedYet = new Message("Test case for update - not exists");
        final Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            messageService.update(notPersistedYet);
        });
        assertEquals("Message with id '0' doesn't exist", thrown.getMessage());
    }

    @Test
    void testDeleteExists() {
        final String text = "Test case for delete - exists";

        final Message message = messageService.create(text);
        assertTrue(messageService.readByText(text).isPresent());

        messageService.delete(message);
        assertTrue(messageService.readByText(text).isEmpty());
    }

    @Test
    void testDeleteNotExists() {
        final Message notPersistedYet = new Message("Test case for delete - not exists");
        final Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            messageService.delete(notPersistedYet);
        });
        assertEquals("Message with id '0' doesn't exist", thrown.getMessage());
    }
}