package dgb.Mp.user;

import dgb.Mp.Role.Role;
import dgb.Mp.Role.RoleRepository;
import dgb.Mp.Role.enums.RoleName;
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
    private final RoleRepository roleRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not found with given id"+id));
    }






    @Transactional
    @Override
    public User createUser(UserDtoToAdd userDtoToAdd, User user) throws MessagingException , MailException {

        /*
        *   Role superAdminRole = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role SUPER_ADMIN not found"));
        System.out.println(superAdminRole.getId());
        if (!user.getRole().getId().equals(1L)) {
            throw new HaveNotPermissionForThat("You, " + user.getUserName() + ", do not have permission to create users. Only SUPER_ADMIN can perform this action.");
        }*/

        Role superAdminRole = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role SUPER_ADMIN not found"));
        System.out.println(superAdminRole.getId());
        if (!user.getRole().getId().equals(1L)) {
            throw new HaveNotPermissionForThat("You, " + user.getUserName() + ", do not have permission to create users. Only SUPER_ADMIN can perform this action.");
        }
        Role userRole = roleRepository.findByName(userDtoToAdd.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDtoToAdd.getRole()));

        User newUser = new User();
        newUser.setEmail(userDtoToAdd.getEmail());
        System.out.println(userDtoToAdd.getEmail());
        newUser.setRole(userRole);
        System.out.println(userRole.getId());
        newUser.setUserName(UserCredentialGenerator.generateUsername(userDtoToAdd.getEmail()));
        String password = UserCredentialGenerator.generatePassword();
        newUser.setPassword(passwordEncoder.encode(password));
        System.out.println("The user credentialls are :**************************");
        System.out.println(password);
        System.out.println(newUser.getUserName());
        System.out.println("*****************************************************");
     //   newUser.setPassword(password);
//
//        if(!mailSenderService.sendEmail(newUser.getEmail(), newUser.getUserName(),password)){
//
//            throw new MailNotSendException("Mail not send to the user :"+newUser.getUserName()+"for some reasons and his password is :"+newUser.getPassword());
//        }

        return userRepository.save(newUser);
    }

    public User registerUser(UserDtoToAdd userDtoToAdd) throws MessagingException , MailException {
        Role userRole = roleRepository.findByName(userDtoToAdd.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDtoToAdd.getRole()));
        User newUser = new User();
        newUser.setEmail(userDtoToAdd.getEmail());
        newUser.setRole(userRole);
        newUser.setUserName(UserCredentialGenerator.generateUsername(userDtoToAdd.getEmail()));
        String password = UserCredentialGenerator.generatePassword();
       newUser.setPassword(passwordEncoder.encode(password));
//newUser.setPassword(password);
        if(!mailSenderService.sendEmail(newUser.getEmail(), newUser.getUserName(),password)){

            throw new MailNotSendException("Mail not send to the user :"+newUser.getUserName()+"for some reasons ");
        }

        return userRepository.save(newUser);

    }
}
