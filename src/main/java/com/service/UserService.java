package com.service;

import com.dtos.RegisterUserDto;
import com.dtos.UpdateUserDto;
import com.dtos.UserDto;
import com.entity.Address;
import com.entity.Contact;
import com.entity.User;
import com.exception.InvalidDataException;
import com.exception.InvalidEmailException;
import com.exception.InvalidLoginException;
import com.exception.InvalidUsernameException;
import com.repository.UserRepository;
import com.validations.EmailValidator;
import com.validations.PasswordValidator;
import com.validations.PhoneValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordValidator passwordValidator;

    private EmailValidator emailValidator;

    private PhoneValidator phoneValidator;

    @Value("${microservice.security.salt}")
    private String salt;

    public UserService(){
         passwordValidator = new PasswordValidator();//New Instance of PasswordValidator is created otherwise exception will be thrown at register-new method
         emailValidator = new EmailValidator();
         phoneValidator = new PhoneValidator();
    }

   public User getUserById(Long id){
        if (id==null){
            throw new InvalidDataException("id cannot be null");
        }
       Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return (user.get());
        }
        throw new InvalidDataException(String.format( "User not found with id %s",id)) ;
   }

    public String createUser(UserDto userDto){
        User user=new User();
       user.setId(userDto.getId());
       user.setUsername(userDto.getUsername());
       userRepository.save(user);
       return null;
    }


    public User getUserByUsername(String username){
        if(username==null) {
            throw new InvalidUsernameException("Username Cannot be Null");
        }
      return  userRepository.findByUsername(username);
    }


    public void checkIfUsernameExists(String username){
        User userByUsername = getUserByUsername(username);//If the username does not already exist it must return null
        if(userByUsername!=null){
            String message=String.format("Username %s already exists for UserId %s",
                    userByUsername.getUsername(),userByUsername.getId());
                log.error(message);
                throw new InvalidDataException(message);
        }
    }

    private User getUserByEmail(String email) {
        if(email==null){
            throw new InvalidEmailException("Email cannot be null or null");
        }
        return userRepository.findByEmail(email);
    }

    private void checkIfEmailExists(String email) {
        User userByEmail = getUserByEmail(email);
        if(userByEmail!=null){
            String message=String.format("Email %s already exists",userByEmail.getEmail());
            throw new InvalidDataException(message);
        }
    }

    public void addAddress(User user,Address address){
        user.setAddress(address);
        address.setUser(user);

    }

    public void addContact(User user,Contact contact){
        user.setContact(contact);
        contact.setUser(user);
    }


    //Register New User
    public User registerUser(RegisterUserDto registerUserDto){
            if(registerUserDto==null){
                throw new InvalidDataException("User data cannot be null");
            }
            checkIfUsernameExists(registerUserDto.getUsername());
            passwordValidator.checkPassword(registerUserDto.getPassword());
            emailValidator.checkEmailIsValidOrNot(registerUserDto.getEmail());

            checkIfEmailExists(registerUserDto.getEmail());
            User user=new User();
            user.setUsername(registerUserDto.getUsername());
            user.setPassword(PasswordEncryptionService.encryptWithSalt(registerUserDto.getPassword(),salt));

            user.setFirstName(registerUserDto.getFirstName());
            user.setLastName(registerUserDto.getLastName());

            user.setGender(registerUserDto.getGender());
            user.setEmail(registerUserDto.getEmail());

        User userRegistered = userRepository.save(user);
        return userRegistered;
    }


    @Transactional
    public User login(String username, String password){
       if((username == null||username.isEmpty()) || (password == null||password.isEmpty())){
            throw new InvalidLoginException("username or password cannot be null");
       }
        User user = getUserByUsername(username);
       if (user==null){
            throw new InvalidLoginException("Invalid username or password");
       }
        log.info(String.format("Login request from %s", username));

       if(PasswordEncryptionService.isPasswordValid(password, user.getPassword(), salt)){
            System.out.println("valid password");
        }
        else {
            throw new InvalidLoginException("Invalid username or password");

        }
        return user;
    }


    public Iterable<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    public List<UserDto> getUserList(){
        ArrayList<UserDto> listDto=new ArrayList<>();
        Iterable<User> users = retrieveAllUsers();
        users.forEach(a->listDto.add(new UserDto(a)));

        return listDto;
    }


    public User UpdateUser(Long id, UpdateUserDto updateUserDto){
        if(id==null){
            throw new InvalidDataException("id cannot be null");
        }
        if(updateUserDto==null){
            throw new InvalidDataException("user data cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()){
            throw new InvalidDataException("User doesnt exist with the given id");
        }
        User user = userOpt.get();
        User userByUsername = getUserByUsername(updateUserDto.getUsername());
        if (userByUsername!=null){
            if(!user.getId().equals(userByUsername.getId())){
                String message=String.format("the username %s already exists with user Id %s",userByUsername.getId(),
                        userByUsername.getUsername());
                log.error(message);
                throw new InvalidDataException(message);
            }
        }
        passwordValidator.checkPassword(updateUserDto.getPassword());
        emailValidator.checkEmailIsValidOrNot(updateUserDto.getEmail());
        phoneValidator.checkPhoneNumber(updateUserDto.getPhoneNumber());

        User userByEmail = getUserByEmail(updateUserDto.getEmail());
        if(userByEmail!=null){
            if(!user.getId().equals(userByEmail.getId())){
                String message=String.format("the user with id %s already exists with email %s",userByEmail.getId(),
                        userByEmail.getEmail());
                log.error(message);
                throw new InvalidDataException(message);
            }
        }

        //Here we are updating user details after checking the req conditions such as username already existing with other id similarly email
        user.setUsername(updateUserDto.getUsername());
        user.setPassword(PasswordEncryptionService.encryptWithSalt(updateUserDto.getPassword(),salt));
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());

        user.setEmail(updateUserDto.getEmail());
        user.setGender(updateUserDto.getGender());

        //CONTACT
        Contact contact = user.getContact();//Here contact is null
        if(contact==null){
            contact=new Contact();
        }
        contact.setPhoneNumber(updateUserDto.getPhoneNumber());
        addContact(user,contact);

        //ADDRESS
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setAddress(updateUserDto.getAddress());
        address.setCity(updateUserDto.getCity());
        address.setState(updateUserDto.getState());
        address.setCountry(updateUserDto.getCountry());
        addAddress(user,address);

        User updatedUser = userRepository.save(user);

        log.info(String.format("user with id %s is updated",user.getId()));

        return updatedUser;
    }


}
