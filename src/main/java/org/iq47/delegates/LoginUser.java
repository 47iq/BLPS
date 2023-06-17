package org.iq47.delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.User;
import org.iq47.model.repo1.UserRepository;
import org.iq47.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("loginUser")
public class LoginUser implements JavaDelegate {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String username = (String) delegateExecution.getVariable("username");
            String password = (String) delegateExecution.getVariable("password");
            String role = (String) delegateExecution.getVariable("role");


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            authentication.getAuthorities();
            if (role.equals("admin")) {
                User user = (User) authentication.getPrincipal();
                if (!user.getRole().equals(role)) {
                    throw new Exception("jopa");
                }
            }

            delegateExecution.setVariable("username", username);
        } catch (BadCredentialsException e){
            throw new BpmnError("BadCredentialsException");
        }
    }
}
