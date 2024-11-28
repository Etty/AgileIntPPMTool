package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {
//        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
//            username has to be unique
//            make sure that pass and confirmpass match
            return userRepository.save(newUser);
//        } catch () {
//
//        }
    }
}
