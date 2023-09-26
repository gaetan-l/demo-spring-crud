package cypherf.demo.springcrud;

import cypherf.demo.springcrud.model.Message;
import cypherf.demo.springcrud.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * {@literal @}ComponentScan scans for {@literal @}Bean, {@literal @}Component,
 * {@literal @}Controller, {@literal @}Service, and {@literal @}Repository. By
 * default, (with no parameter) it scans in the current package + sub-packages.
 * <p>
 * {@literal @}EnableJpaRepositories is needed to check for Repositories inheriting
 * from Spring Data interfaces, i.e. extending CrudRepository.
 * Cf. MessageRepository
 */
@ComponentScan
@EnableJpaRepositories
public class Application {
	@Autowired
	private MessageRepository messageRepository;

	final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		Application app = ctx.getBean(Application.class);
		app.run(args);
	}

	public void run(String... args) {
		// SAVE
		logger.debug("saving message 1/one...");
		Message messageOne = messageRepository.save(new Message("Message one"));

		logger.debug("saving message 2/two...");
		Message messageTwo = messageRepository.save(new Message("Message two"));

		// SAVE ALL
		logger.debug("saving messages 3/three to 8/eight...");
		Message messageThree = new Message("Message three");
		Message messageFour = new Message("Message four");
		Message messageFive = new Message("Message five");
		Message messageSix = new Message("Message six");
		Message messageSeven = new Message("Message seven");
		Message messageEight = new Message("Message eight");
		List<Message> messages = Arrays.asList(messageThree, messageFour, messageFive, messageSix, messageSeven, messageEight);
		messageRepository.saveAll(messages);

		// COUNT
		logger.debug("counting messages...");
		logger.debug("Number of messages: " + messageRepository.count());

		// EXISTS
		long idFake = 0;
		logger.debug("checking if message by id=" + idFake + " exists...");
		boolean exists = messageRepository.existsById(idFake);
		logger.debug("Message with id=" + idFake + (exists ? " exists" : " doesn't exist"));

		long idOne = messageOne.getId();
		logger.debug("checking if message by id=" + idOne + " exists...");
		exists = messageRepository.existsById(idOne);
		logger.debug("Message with id=" + idOne + (exists ? " exists" : " doesn't exist"));

		// FIND BY ID
		logger.debug("finding message by id=" + idOne + "...");
		Optional<Message> dbMessageOne = messageRepository.findById(idOne);
		if (dbMessageOne.isEmpty()) {
			logger.debug("Message with id=" + idOne + " not found");
		}
		else {
			logger.debug("Message with id=" + idOne + " found: " + dbMessageOne.get());
		}

		// FIND ALL BY ID
		long idTwo = messageTwo.getId();
		List<Long> ids = Arrays.asList(idOne, idTwo);
		logger.debug("finding messages by ids=" + ids + "...");
		messageRepository.findAllById(ids).forEach(m -> {
			logger.debug("Message: " + m.toString());
		});

		// FIND ALL
		printAllMessages();

		// DELETE
		logger.debug("deleting message " + messageEight.toString());
		messageRepository.delete(messageEight);
		printAllMessages();

		// DELETE BY ID
		logger.debug("deleting message with id=" + idFake);
		messageRepository.deleteById(idFake);
		printAllMessages();

		Long idSeven = messageSeven.getId();
		logger.debug("deleting message with id=" + idSeven);
		messageRepository.deleteById(idSeven);
		printAllMessages();

		// DELETE ALL
		List<Message> messageGroup = Arrays.asList(messageSix, messageFive);
		logger.debug("deleting messages " + messageGroup);
		messageRepository.deleteAll(messageGroup);
		printAllMessages();

		// DELETE ALL BY ID
		List<Long> idGroup = Arrays.asList(messageFour.getId(), messageThree.getId());
		logger.debug("deleting messages with ids " + idGroup);
		messageRepository.deleteAllById(idGroup);
		printAllMessages();

		// FIND BY TEXT
		logger.debug("finding all messages by text '" + messageOne.getText() + "'");
		messageRepository.findByText(messageOne.getText()).forEach(m -> {
			logger.debug("Message: " + m.toString());
		});

		logger.debug("finding all messages by text '" + messageTwo.getText() + "'");
		messageRepository.findByText(messageTwo.getText()).forEach(m -> {
			logger.debug("Message: " + m.toString());
		});
	}

	private void printAllMessages() {
		logger.debug("finding all messages...");
		messageRepository.findAll().forEach(m -> {
			logger.debug("Message: " + m.toString());
		});
	}
}