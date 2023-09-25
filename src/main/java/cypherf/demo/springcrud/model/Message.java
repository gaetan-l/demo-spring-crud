package cypherf.demo.springcrud.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Message {
	final static Logger logger = LoggerFactory.getLogger(Message.class);

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true, nullable = false)
	private String text;

	@Deprecated
	protected Message() {};

	public Message(String name) {
		this.text = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return String.format("Message [id: %d; text:\"%s\"]", id, text);
	}
}