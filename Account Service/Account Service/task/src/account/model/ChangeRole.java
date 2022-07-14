package account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRole {

    private String user;
    private String role;
    private String operation;
}
