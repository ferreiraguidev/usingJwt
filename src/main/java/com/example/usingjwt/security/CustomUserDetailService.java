package com.example.usingjwt.security;

import com.example.usingjwt.domain.AppUser;
import com.example.usingjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@EnableWebSecurity
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser applicationUser = loadApplicationUserByUsername(username);
        return new CustomUserDetails(applicationUser);
    }

    public AppUser loadApplicationUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("ApplicationUser not found"));
    }

    private final static class CustomUserDetails extends AppUser implements UserDetails {
        private CustomUserDetails(AppUser applicationUser) {
            super();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList("ROLE_PROFESSOR");
            List<GrantedAuthority> authorityListStudent = AuthorityUtils.createAuthorityList("ROLE_STUDENT");
            return this.getAuthorities() != null ? authorityListProfessor : authorityListStudent;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}


