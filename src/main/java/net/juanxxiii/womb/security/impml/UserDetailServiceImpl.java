package net.juanxxiii.womb.security.impml;

import net.juanxxiii.womb.database.entities.UserLoginSystem;
import net.juanxxiii.womb.database.entities.Users;
import net.juanxxiii.womb.database.repositories.UserLoginRepository;
import net.juanxxiii.womb.database.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private UserLoginRepository usersRepository;

    public UserDetailServiceImpl(UserLoginRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginSystem user = usersRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
