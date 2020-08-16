package com.zannier.exam.medico.infraestructure.security;

import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MedicoSecurityService implements UserDetailsService {

    @Autowired
    private MedicoService medicoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Medico medico = medicoService.obtenerPorDocumento(s);

        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(medico.getRol()));

        UserDetails userDetails = new User(medico.getDni(),medico.getClave(),roles);

        return userDetails;
    }
}
