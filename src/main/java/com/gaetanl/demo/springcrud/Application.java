package com.gaetanl.demo.springcrud;

import com.gaetanl.demo.springcrud.model.Message;
import com.gaetanl.demo.springcrud.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

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
	private MessageService messageService;

	final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		Application app = ctx.getBean(Application.class);
		app.run(args);
	}

	public void run(String... args) {
		// SAVE
		logger.info("creating message 1/one...");
		Message messageOne = messageService.create("Message one");

		logger.info("creating message 2/two...");
		Message messageTwo = messageService.create("Message two");

		logger.info("creating message 3/three...");
		Message messageThree = messageService.create("Message three");

		logger.info("creating message 4/four...");
		Message messageFour = messageService.create("Message four");

		logger.info("creating message 5/five...");
		Message messageFive = messageService.create("Message five");

		logger.info("creating message 6/six...");
		Message messageSix = messageService.create("Message six");

		logger.info("creating message 7/seven...");
		Message messageSeven = messageService.create("Message seven");

		logger.info("creating message 8/eight...");
		Message messageEight = messageService.create("Message eight");

		// COUNT
		logger.info("counting messages...");
		logger.info("Number of messages: " + messageService.readAll().size());

		// READ BY ID
		Arrays.asList(0L, messageOne.getId()).forEach(id -> {
			logger.info("checking if message with id=" + id + " exists...");
			logger.info("Message with id=" + id + (messageService.readById(id).map(message -> " found: " + message).orElse(" not found")));
		});

		// FIND ALL
		printAllMessages();

		// DELETE
		logger.info("deleting message " + messageEight);
		messageService.delete(messageEight);
		printAllMessages();

		// FIND BY TEXT
		Arrays.asList("fake text", messageOne.getText(), messageTwo.getText()).forEach(text -> {
			logger.info("checking if message with text='" + text + "' exists...");
			logger.info("Message with text='" + text + (
					messageService.readByText(text)
							.map(message -> "' found: " + message)
							.orElse("' not found")));
		});
	}

	private void printAllMessages() {
		logger.info("finding all messages...");
		messageService.readAll().forEach(m -> logger.info("Message: " + m));
	}
}