package thelazycoder.blog_app.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class InfoGetter {

    private final UserRepository userRepository;

    @Transactional
    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails){
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            return userRepository.findByEmail(username)
                    .orElseThrow(
                            ()-> new UsernameNotFoundException("Username not found "+ username));
        }
        return null;
    }
}
