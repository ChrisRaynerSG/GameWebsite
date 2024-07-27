package com.sparta.cr.carnasagameswebsite.service;

import com.sparta.cr.carnasagameswebsite.models.SecurityUser;
import com.sparta.cr.carnasagameswebsite.models.User;
import com.sparta.cr.carnasagameswebsite.repositories.UserRepository;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class SiteUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    @Autowired
    public SiteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    public void updatePassword(User user, String newPassword) {
        if(validatePasswordFormat(newPassword)) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        //else throw new InvalidPasswordException(); (need to make exception)
    }
    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }
    public void updateProfileImage(User user, MultipartFile newProfileImage) throws IOException, FileSizeLimitExceededException {
        if(newProfileImage != null && !newProfileImage.isEmpty()) {
            if(newProfileImage.getSize() > MAX_FILE_SIZE) {
                throw new FileSizeLimitExceededException("Image file is larger than " + MAX_FILE_SIZE +"MB!", newProfileImage.getSize(), MAX_FILE_SIZE);
            }
            String base64Image = Base64.getEncoder().encodeToString(newProfileImage.getBytes());
            user.setProfileimage(base64Image);
        }
        userRepository.save(user);
    }
    public void updateDescription(User user, String newDescription) {
        user.setDescription(newDescription);
        userRepository.save(user);
    }
    public void updateRole(User user, String role) {
        user.setRoles("ROLE_" + role);
        userRepository.save(user);
    }


    public boolean validateUserPasswordCombination(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    public boolean validatePasswordFormat(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean validateEmailFormat(String email){
        return email.matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$");
    }

    //todo new table to prevent user following someone more than once
//    public void addFollower(User user){
//        user.setFollowers(user.getFollowers() + 1);
//        userRepository.save(user);
//    }
//    public void removeFollower(User user){
//        user.setFollowers(user.getFollowers() - 1);
//    }
}
