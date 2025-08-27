package TMDT.example.TMDT.Users.DTO.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
    private Object data;
    private String message;
    private boolean success=false;


}
