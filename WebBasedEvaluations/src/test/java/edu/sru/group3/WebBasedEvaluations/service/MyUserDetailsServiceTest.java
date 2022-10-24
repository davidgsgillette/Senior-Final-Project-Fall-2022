package edu.sru.group3.WebBasedEvaluations.service;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

/**Service for finding/loading particular users.
 * @author Dalton Stenzel
 *
 */
@Suite
@SuiteDisplayName("My User Details Service Methods")
@IncludeClassNamePatterns(".*Tests")
public class MyUserDetailsServiceTest {

    @Mock
    static MyUserDetailsService MyUserDetSer;

    @Test
    public void loadUserByUsernameTests() {
            MyUserDetSer = new MyUserDetailsService();
            UserDetails actual = MyUserDetSer.loadUserByUsername("Jon");
            assertNotNull(actual);
            
            
        }
}