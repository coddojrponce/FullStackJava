package com.robertponce.W5L2.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.robertponce.W5L2.models.Login;
import com.robertponce.W5L2.models.User;
import com.robertponce.W5L2.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User registerUser(User u,BindingResult result) {
    	// TO-DO - Reject values or register if no errors:
        
        // Reject if email is taken (present in database)
		Optional<User> exists = userRepo.findByEmail(u.getEmail());
		if(exists.isPresent()) {
			result.rejectValue("email","Matches", "Email already in use");
		}
        // Reject if password doesn't match confirmation
		if(!u.getConfirm().equals(u.getPassword())) {
			result.rejectValue("confirm","Matches", "Passwords do not match");

		}
        
        // Return null if result has errors
		if(result.hasErrors()) {
		    // Exit the method and go back to the controller 
		    // to handle the response
		    return null;
		}
    
        // Hash and set password, save user to database
		String hashed = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
		u.setPassword(hashed);
		
		return userRepo.save(u);
	}
	
    // This method will be called from the controller
    // whenever a user submits a login form.
        public User login(Login login, BindingResult result) {
        // TO-DO - Reject values:
    		
    	// Find user in the DB by email
        Optional<User> exists = userRepo.findByEmail(login.getEmail());
        User user = null ;
        // Reject if NOT present
        if(exists.isEmpty()) {
			result.rejectValue("email","Matches", "email does not exist");
            result.rejectValue("password", "Matches", "Invalid Password!");

        }else {
        	user = exists.get();
        	if(!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
              result.rejectValue("password", "Matches", "Invalid Password!");
        	}
        }
        
//        // Reject if BCrypt password match fails
//        if(!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
//            result.rejectValue("password", "Matches", "Invalid Password!");
//        }
//    
        // Return null if result has errors
        if(result.hasErrors()) {
        	return null;
        }
        
        return user;
        // Otherwise, return the user object
    }
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
		}
	
	public User getUserById(Long id) {
		Optional<User> exists =  userRepo.findById(id);
		if(exists.isEmpty()) {
			return null;
		}
		
		return exists.get();
	}

}
