package dgb.Mp.user;

import dgb.Mp.user.Dtos.UserDtoToAdd;
import jakarta.mail.MessagingException;
import org.springframework.mail.MailException;

public interface UserService {

    User getUser(Long id);



    User createUser(UserDtoToAdd userDtoToAdd,User user) throws MessagingException;


    User    registerUser(UserDtoToAdd userDtoToAdd) throws MessagingException , MailException;
}
