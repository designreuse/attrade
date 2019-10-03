package by.attrade.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@RequiredArgsConstructor
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

    @Column(length = 2048)
    @NotBlank(message = "Please fill the message.")
    @Length(max = 2048, message = "Message is too long. More than 2kB.")
    private String text;

    @Column(length = 255)
    @Length(max = 255, message = "Message is too long. More than 255B.")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(length = 255)
    private String filename;
}
