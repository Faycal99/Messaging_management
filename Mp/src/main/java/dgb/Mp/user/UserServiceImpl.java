package dgb.Mp.user;

import dgb.Mp.Utils.MailSenderService;
import dgb.Mp.user.Dtos.UserDtoToAdd;
import dgb.Mp.Utils.UserCredentialGenerator;
import dgb.Mp.generalAdvice.customException.HaveNotPermissionForThat;
import dgb.Mp.generalAdvice.customException.MailNotSendException;
import dgb.Mp.generalAdvice.customException.UserNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not found with given id"+id));
    }




    @Transactional
    @Override
    public User createUser(UserDtoToAdd userDtoToAdd, User user) throws MessagingException , MailException {
        if(!user.getRole().equals(Role.ROLE_ADMIN)){

            throw new HaveNotPermissionForThat("you "+user.getUserName()+" have any permission to add this user") ;
        }

        User newUser = new User();
        newUser.setEmail(userDtoToAdd.getEmail());
        newUser.setRole(Role.ROLE_USER);
        newUser.setUserName(UserCredentialGenerator.generateUsername(userDtoToAdd.getEmail()));
        String password = UserCredentialGenerator.generatePassword();
        newUser.setPassword(passwordEncoder.encode(password));

        if(!mailSenderService.sendEmail(newUser.getEmail(), newUser.getUserName(),password)){

            throw new MailNotSendException("Mail not send to the user :"+newUser.getUserName()+"for some reasons ");
        }

        return userRepository.save(newUser);
    }

    public User registerUser(UserDtoToAdd userDtoToAdd) throws MessagingException , MailException {

        User newUser = new User();
        newUser.setEmail(userDtoToAdd.getEmail());
        newUser.setRole(userDtoToAdd.getRole());
        newUser.setUserName(UserCredentialGenerator.generateUsername(userDtoToAdd.getEmail()));
        String password = UserCredentialGenerator.generatePassword();
        newUser.setPassword(passwordEncoder.encode(password));

        if(!mailSenderService.sendEmail(newUser.getEmail(), newUser.getUserName(),password)){

            throw new MailNotSendException("Mail not send to the user :"+newUser.getUserName()+"for some reasons ");
        }

        return userRepository.save(newUser);

    }
}
