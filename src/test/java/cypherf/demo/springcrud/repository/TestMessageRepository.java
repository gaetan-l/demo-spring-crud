package cypherf.demo.springcrud.repository;

import cypherf.demo.springcrud.model.Message;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ComponentScan
@EnableJpaRepositories
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestMessageRepository {
    @Autowired
    private MessageRepository messageRepository;

    static private Message msg1;
    static private Message msg2;
    static private Message msg3;
    static private Message msg4;
    static private Message msg5;
    static private Message msg6;

    @BeforeAll
    static void setUp() {
        msg1 = new Message("Greetings ladies and gentlemen");
        msg2 = new Message("Welcome to TestMessageRepository");
        msg3 = new Message("We are going to test the various Message persistence methods");
        msg4 = new Message("Namely the CRUD operations create, read, update and delete");
        msg5 = new Message("This shouldn't take too long");
        msg6 = new Message("Thank you for your patience");
    }

    @Test
    @Order(1)
    void testFindAllEmpty() {
        assertFalse(messageRepository.findAll().iterator().hasNext());
    }
}