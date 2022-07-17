package com.example.AccountService.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonLocked;
    private int failedAttempt;
    private Date lockTime;

//    public UserDetailsImpl(User user) {
//        this.email = user.getEmail();
//        this.password = user.getPassword();
//        this.authorities = null;
//    }

    public UserDetailsImpl(String email, String password, Collection<? extends GrantedAuthority> authorities,
                           boolean enabled, boolean accountNonLocked, int failedAttempt, Date lockTime) {

        if (((email == null) || "".equals(email)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }

        this.email = email;
        this.password = password;
        this.authorities = null;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.failedAttempt = failedAttempt;
        this.lockTime = lockTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public int getFailedAttempt() {
        return this.failedAttempt;
    }

    public Date getLockTime() {
        return this.lockTime;
    }

    public static final class CustomUserBuilder {

        private String email;
        private String password;
        private Collection<GrantedAuthority> authorities;
        private boolean enabled;
        private boolean accountNonLocked;
        private int failedAttempt;
        private Date lockTime;

        private CustomUserBuilder() {
        }

        public static CustomUserBuilder aCustomUser() {
            return new CustomUserBuilder();
        }

        public CustomUserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public CustomUserBuilder withAuthorities(Collection<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUserBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public CustomUserBuilder withFailedAttempt(int failedAttempt) {
            this.failedAttempt = failedAttempt;
            return this;
        }

        public CustomUserBuilder withLockTime(Date lockTime) {
            this.lockTime = lockTime;
            return this;
        }

        public UserDetailsImpl build() {
            UserDetailsImpl userDetailsImpl = new UserDetailsImpl(email, password, authorities,
                    enabled, accountNonLocked, failedAttempt, lockTime);
            userDetailsImpl.authorities = this.authorities;
            return userDetailsImpl;
        }
    }
}
