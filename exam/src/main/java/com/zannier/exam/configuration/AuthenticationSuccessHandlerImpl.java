package com.zannier.exam.configuration;

import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HttpSession httpSession;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        Medico medico = medicoService.obtenerPorDocumento(username);

        if (medico != null) {
            httpSession.setAttribute("medicoLogin", medico);
        }

        String url = "/home";

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, url);

    }
}
