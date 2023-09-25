package cypherf.demo.springcrud.repository;

import cypherf.demo.springcrud.Application;
import cypherf.demo.springcrud.model.Message;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    static private final String msg1String = "Greetings ladies and gentlemen";
    static private final String msg2String = "Welcome to MessageRepositoryTest";
    static private final String msg3String = "We are going to test the various Message persistence methods";
    static private final String msg4String = "Namely the CRUD operations create, read, update and delete";
    static private final String msg5String = "This shouldn't take too long";
    static private final String msg6String = "Thank you for your patience";
    static private final String msg7String = "Beginning tests in 3, 2, 1...";
    static private final String msg8String = "Here we go";

    static private Message msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8;

    @BeforeAll
    static void setUp() {
        msg1 = new Message(msg1String);
        msg2 = new Message(msg2String);
        msg3 = new Message(msg3String);
        msg4 = new Message(msg4String);
        msg5 = new Message(msg5String);
        msg6 = new Message(msg6String);
        msg7 = new Message(msg7String);
        msg8 = new Message(msg8String);
    }

    @Test
    @Order(1)
    void testCountEmpty() {
        assertEquals(0, messageRepository.count());
    }

    @Test
    @Order(2)
    void testSave() {
        msg1 = messageRepository.save(msg1);
        msg2 = messageRepository.save(msg2);

        assertNotEquals(0L, msg1.getId());
        assertNotEquals(0L, msg2.getId());

        assertEquals(msg1.getText(), msg1String);
        assertEquals(msg2.getText(), msg2String);
    }

    @Test
    @Order(3)
    void testCount() {
        assertEquals(2, messageRepository.count());
    }

    @Test
    @Order(4)
    void testExistsById() {
        assertTrue(messageRepository.existsById(msg1.getId()));
        assertTrue(messageRepository.existsById(msg2.getId()));
        assertFalse(messageRepository.existsById(0L));
    }

    @Test
    @Order(5)
    void testFindById() {
        Message res1 = messageRepository.findById(msg1.getId()).get();
        Message res2 = messageRepository.findById(msg2.getId()).get();

        assertEquals(msg1.getId(), res1.getId());
        assertEquals(msg1.getText(), res1.getText());
        assertEquals(msg2.getId(), res2.getId());
        assertEquals(msg2.getText(), res2.getText());

        assertTrue(messageRepository.findById(0L).isEmpty());
    }

    @Test
    @Order(6)
    void testFindByText() {
        assertEquals(1, messageRepository.findByText(msg1String).size());
        assertEquals(1, messageRepository.findByText(msg2String).size());
        assertEquals(0, messageRepository.findByText("Random string").size());
    }

    @Test
    @Order(7)
    void testSaveAllFindAll() {
        List<Message> saved = Arrays.asList(msg3, msg4, msg5, msg6, msg7, msg8);
        List<String> result = new ArrayList<>();

        messageRepository.saveAll(saved);
        messageRepository.findAll().forEach(m -> {
            result.add(m.getText());
        });

        assertEquals(8, result.size());
        saved.forEach(m -> {
            assertTrue(result.contains(m.getText()));
        });
    }

    @Test
    @Order(8)
    void testDelete() {
        long msg8id = msg8.getId();
        long msg7id = msg7.getId();

        messageRepository.delete(msg8);
        messageRepository.delete(msg7);

        assertFalse(messageRepository.existsById(msg8id));
        assertFalse(messageRepository.existsById(msg7id));
    }

    @Test
    @Order(9)
    void testDeleteById() {
        long msg6id = msg6.getId();
        long msg5id = msg5.getId();

        messageRepository.deleteById(msg6id);
        messageRepository.deleteById(msg5id);
        messageRepository.deleteById(0L);

        assertFalse(messageRepository.existsById(msg6id));
        assertFalse(messageRepository.existsById(msg5id));
    }

    @Test
    @Order(10)
    void testDeleteAllById() {
        long msg4id = msg4.getId();
        long msg3id = msg3.getId();
        List<Long> ids = Arrays.asList(msg4id, msg3id);

        messageRepository.deleteAllById(ids);

        assertFalse(messageRepository.existsById(msg4id));
        assertFalse(messageRepository.existsById(msg3id));
    }

    @Test
    @Order(11)
    void testUpdate() {
        String updatedText = "UpdatedText";
        msg2 = messageRepository.findById(msg2.getId()).get();
        msg2.setText(updatedText);
        messageRepository.save(msg2);

        msg2 =  messageRepository.findById(msg2.getId()).get();
        assertEquals(updatedText, msg2.getText());
    }

    @Test
    @Order(12)
    void testDeleteAll() {
        messageRepository.deleteAll();

        assertEquals(0, messageRepository.count());
    }
}