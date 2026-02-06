package com.hekimasist.backend.service;

import java.util.Collections;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hekimasist.backend.entity.User;
import com.hekimasist.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String tcNo) throws UsernameNotFoundException {
    User user = userRepository.findByTcNo(tcNo)
        .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı (TC No): " + tcNo));

    // ÇÖZÜM BURADA: Boş liste yerine kullanıcının rolünü listeye ekliyoruz
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole()) // Veritabanındaki
                                                                             // "DOKTOR" buraya
                                                                             // geliyor
        );

    return new org.springframework.security.core.userdetails.User(user.getTcNo(),
        user.getPassword(), authorities // Spring artık kullanıcının DOKTOR olduğunu biliyor
    );
  }
}