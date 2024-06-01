package common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class MessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessagePK")
    private long MessagePK;

    @Column(name = "CaseIDFK")
    private long caseIDFK;

    @Column(name = "Message")
    private String Message;

    @Column(name = "messageDirection")
    private char messageDirection;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

}
