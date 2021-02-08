package com.mark.gerasimov.highload.security;

import com.mark.gerasimov.highload.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public DBUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        return userDao.findByName(name)
                .map(DbUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(name));
    }
}
