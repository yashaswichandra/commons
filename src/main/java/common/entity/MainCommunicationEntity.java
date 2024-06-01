package common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maincommunication")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class MainCommunicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CaseID")
    private long CaseID;

    @Column(name = "CommunicationFrom")
    private long communicationFrom;

    @Column(name = "CommunicationTo")
    private long communicationTo;

}
