package com.example.project2.security;

import java.util.*;

import com.example.project2.domain.Member;
import com.example.project2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;



@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = mapper.selectById(username);

        if (member == null) {
            throw new UsernameNotFoundException(username + " 회원이 없습니다.");
        }

        UserDetails user = User.builder()
                .username(member.getId())
                .password(member.getPassword())
//				.authorities(member.getAuthority().stream().map(SimpleGrantedAuthority::new).toList())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority())))
                .build();

        return user;
    }
}