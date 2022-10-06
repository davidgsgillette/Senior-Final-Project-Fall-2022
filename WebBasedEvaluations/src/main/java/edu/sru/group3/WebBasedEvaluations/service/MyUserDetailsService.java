package edu.sru.group3.WebBasedEvaluations.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

import java.util.Optional;

/**Service for finding/loading particular users.
 * @author Dalton Stenzel
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**Method for returning a user found with a specific email if the user exists.
     *
     */
    @Override
    public UserDetails loadUserByUsername(String email) 
    	throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email);
            
            
//            //This gets the currently logged in user. 
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String userName = ((UserDetails)principal).getUsername();
//            User currentUser = userRepository.findByEmail(userName);
//            
            
            
            
            /*
            if(currentUser == null) {
            	throw new UsernameNotFoundException("finding pricipal did not work.");
            }
            else */if (user == null) {
                throw new UsernameNotFoundException("Could not find user");
            }
//            else if (currentUser.getCompany().getId() != user.getCompany().getId()) {
//                throw new UsernameNotFoundException("That user does not exist in your organization.");
//            }
             
            return new MyUserDetails(user);
        }
}