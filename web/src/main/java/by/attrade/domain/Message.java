package by.attrade.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Message implements Serializable {
    public static final long serialVersionUID = 2L;
    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the message.")
    @Length(max = 2048, message = "Message is too long. More than 2kB.")
    private String text;
    @Length(max = 255, message = "Message is too long. More than 255B.")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;
}
