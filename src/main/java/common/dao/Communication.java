package common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Communication
{


    private Long fromId;
    private long toId;
    private String message;
    private char messageDirection;
    private long caseId;
    private long messageFk;
    private LocalDateTime createdDate;
}
