package common.dao;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class Password {
    private String salt;
    private String encryptedPassword;
}
