package com.ofkMasay.Service;

import com.ofkMasay.Entity.AccessToken;
import com.ofkMasay.Entity.User;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Repository.TokenRepository;
import com.ofkMasay.Repository.UserRepository;
import com.ofkMasay.Request.LoginRequest;
import com.ofkMasay.Response.LoginResponse;
import com.ofkMasay.Response.RegisterResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepo;
    private final TokenRepository tokenRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepo, TokenRepository tokenRepository) {
        this.userRepo = userRepo;
        this.tokenRepository = tokenRepository;
    }

    public RegisterResponse createUser(User user){
        try{
            if(userRepo.createUser(user)){
                User savedUser = userRepo.findByUsername(user.getUsername());
                return new RegisterResponse(savedUser);
            }else {
                throw new CustomException("User could not be registered!");
            }
        }catch (Exception e){
            throw new CustomException("User could not be registered due to: "+e.getMessage());
        }
    };

    public List<User> getAllUsers() {
        try{
            return userRepo.getUsers();
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public User getUserById(Long id) {
        try{
            return userRepo.getUserById(id);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }


    public boolean updateUser(Long id, User user) {
        try{
            return userRepo.updateUser(id, user);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public boolean deleteUser(Long id) {
        try{
            return userRepo.deleteUser(id);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        try{
           User userFound = userRepo.findByUsername(loginRequest.getUsername());
            if(passwordEncoder.matches(loginRequest.getPassword(), userFound.getPassword())){
                AccessToken token = tokenRepository.createToken(userFound.getId());
                return new LoginResponse(userFound, token);
            }else{
                throw new CustomException("Login is not successful");
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }


    public void deleteTokenByUserId(Long userId) {
        try{
            tokenRepository.deleteTokenByUserId(userId);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public boolean isAccessTokenValid(String token) {
        try{
            AccessToken accessToken = tokenRepository.getAccessTokenByToken(token);
            if (accessToken != null) {
                return !accessToken.getExpiryDate().before(Timestamp.from(Instant.now()));
            }else return false;
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }
}
