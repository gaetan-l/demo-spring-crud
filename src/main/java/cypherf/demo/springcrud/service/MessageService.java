package cypherf.demo.springcrud.service;

import cypherf.demo.springcrud.model.Message;
import cypherf.demo.springcrud.repository.MessageRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public @NonNull Message create(@NonNull String text) {
        if (readByText(text).isPresent()) throw new EntityExistsException(String.format("Message with text '%s' already exists", text));
        return messageRepository.save(new Message(text));
    }

    public @NonNull Optional<Message> readById(@NonNull Long id) {
        return messageRepository.findById(id);
    }

    public @NonNull Optional<Message> readByText(@NonNull String text) {
        final Iterator<Message> found = messageRepository.findByText(text).iterator();

        if (found.hasNext()) {
            final Message result = found.next();
            if (found.hasNext()) throw new NonUniqueResultException(String.format("Non unique message with text '%s'", text));
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }

    public @NonNull List<Message> readAll() {
        return messageRepository.findAll();
    }

    public @NonNull Message updateMessage(Message messageToUpdate) {
        long id = messageToUpdate.getId();
        Optional<Message> read = messageRepository.findById(id);
        if (read.isEmpty()) throw new jakarta.persistence.EntityNotFoundException(String.format("Message with id '%d' doesn't exist", id));
        return messageRepository.save(messageToUpdate);
    }
}
