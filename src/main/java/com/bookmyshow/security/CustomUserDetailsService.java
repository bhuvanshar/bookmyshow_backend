package com.bookmyshow.security;

import com.bookmyshow.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return CustomUserDetails.fromUser(user);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
