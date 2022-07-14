package account.security.service;

import account.entity.SecurityEvent;
import account.entity.User;
import account.service.SecurityEventService;
import account.service.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    @Autowired
    SecurityEventService securityEventService;

    @Autowired
    UserService userService;

    private final int MAX_ATTEMPT = 15; //attempts before ip block, not linked to account
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key, String email) {

        //reset user failed attempts to 0 on successful login
        this.userService.resetFailedAttempts(email);

        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key, String uri, String email) {
        int attempts = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;

        System.out.println(attempts);
        User user = this.userService.findUserByEmail(email);
        if (user != null) {
            //increment failed attempt on user
            this.userService.increaseFailedAttempts(user);
            user = this.userService.findUserByEmail(email);
        }
        this.securityEventService.save(
                new SecurityEvent(LocalDateTime.now(), "LOGIN_FAILED", email, uri, uri));
        //log securityEvent BRUTE_FORCE at 5 attempts
        if (user != null && user.getFailedAttempt() == 5) {

            this.securityEventService.save(
                    new SecurityEvent(LocalDateTime.now(), "BRUTE_FORCE", email, uri, uri));

            if (user.getId() != 1) {
                this.userService.lock(user);
            }

            //log security event
            this.securityEventService.save(
                    new SecurityEvent(LocalDateTime.now(), "LOCK_USER", email,
                            "Lock user " + user.getEmail(), uri));
        }

        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}