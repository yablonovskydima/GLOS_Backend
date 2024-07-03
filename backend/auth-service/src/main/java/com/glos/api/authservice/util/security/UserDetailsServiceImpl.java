package com.glos.api.authservice.util.security;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.entities.User;
import com.glos.api.authservice.exception.ResponseEntityException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAPIClient userAPIClient;
    private final UserDatabaseAPIClient userDatabaseAPIClient;

    public UserDetailsServiceImpl(
            UserAPIClient userAPIClient,
            UserDatabaseAPIClient userDatabaseAPIClient
    ) {
        this.userAPIClient = userAPIClient;
        this.userDatabaseAPIClient = userDatabaseAPIClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u2 = getUser(userDatabaseAPIClient.getByUsername(username));
        JwtEntity j = new JwtEntity();
        j.setUser(u2);
        return j;
    }

    private User getUser(ResponseEntity<User> getUserResponse)
            throws UsernameNotFoundException {
        if (getUserResponse.getStatusCode().is2xxSuccessful()) {
            return getUserResponse.getBody();
        } else if (getUserResponse.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("Username not found");
        } else if (getUserResponse.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        }
        throw new ResponseEntityException(getUserResponse);
    }
}
