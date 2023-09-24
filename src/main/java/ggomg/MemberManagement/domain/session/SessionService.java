package ggomg.MemberManagement.domain.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionService {

    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public SessionService() {
        this.securityContextRepository = new HttpSessionSecurityContextRepository();
    }

    public void updateSession(HttpServletRequest request, HttpServletResponse response) {

        SecurityContext context = SecurityContextHolder.getContext();
        securityContextRepository.saveContext(context, request, response);

    }

}
