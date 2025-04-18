package com.example.Lab4.User;

import com.example.Lab4.Annotations.Cache;
import com.example.Lab4.Annotations.DistributedLock;
import com.example.Lab4.User.dto.CreateUserDTO;
import com.example.Lab4.User.dto.UpdateUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String USER_LOCK_PREFIX = "user";

    private static final String ALL_USERS_CACHE_KEY = "users:all"; // Define cache key
    private static final long CACHE_TTL = 60; // Cache TTL in seconds

    @Cache(cacheKey = ALL_USERS_CACHE_KEY, cacheTTL = CACHE_TTL)
        public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(CreateUserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getBalance());
        return userRepository.save(user);
    }


    @DistributedLock(keyPrefix = USER_LOCK_PREFIX, keyIdentifierExpression = "#id", leaseTime = 10, timeUnit = TimeUnit.SECONDS)
    public User updateUser(Long id, UpdateUserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // --- Simulate long-running operation ---
        log.info("User update for id {}: Starting  delay...", id);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.warn("User update for id {}: Sleep interrupted!", id);
            Thread.currentThread().interrupt();
        }
        log.info("User update for id {}: Finished delay. Saving...", id);
        // --- End simulation ---

        BeanUtils.copyProperties(userDTO, existingUser, getNullPropertyNames(userDTO));
        return userRepository.save(existingUser);
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}