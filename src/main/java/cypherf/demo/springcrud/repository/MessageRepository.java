package cypherf.demo.springcrud.repository;

import cypherf.demo.springcrud.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Extension of CrudRepository for a specific domain clas, will be implemented
 * by SimpleJpaRepository.
 * <p>
 * No code needed for basic CRUD operation as they are all implemented in
 * SimpleJpaRepository.
 * <p>
 * &#064;Repository annotation is not needed, I'm guessing MessageRepository is
 * detected thanks to extending CrudRepository + @EnableJpaRepository in main
 * application class.
 * <p>
 * &#064;Transactional  annotation is not needed, by default, methods inherited
 * from CrudRepository inherit the transactional configuration from
 * SimpleJpaRepository. Note that there is still need for a TransactionManager,
 * Cf. PersistenceConfig.transactionManager()
 * Cf. <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions">...</a>
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
    /**
     * Specific functions can be added (not implemented) using the names of the
     * domain class members
     * Cf. <a href="https://spring.io/guides/gs/accessing-data-jpa/#_create_simple_queries">...</a>
     */
    List<Message> findByText(String text);
}
