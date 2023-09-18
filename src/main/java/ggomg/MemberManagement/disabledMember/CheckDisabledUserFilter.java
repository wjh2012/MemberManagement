package ggomg.MemberManagement.disabledMember;

import ggomg.MemberManagement.security.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class CheckDisabledUserFilter extends OncePerRequestFilter {

    private final DisableMemberRepository disableMemberRepository;

    public CheckDisabledUserFilter(DisableMemberRepository disableMemberRepository) {
        this.disableMemberRepository = disableMemberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            log.info("when authenticate()");
            CustomUser customUser = (CustomUser) authentication.getPrincipal();

            try {
                Optional<DisabledMember> disabledMember = disableMemberRepository.findByMemberId(customUser.getId());
                disabledMember.ifPresent(this::handleDisabledMember);
            } catch (Exception e) {
                // 예외 처리 - 클라이언트에게 오류 응답 반환 등
                log.error("An error occurred while checking for disabled member.", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleDisabledMember(DisabledMember disabledMember) {
        SecurityContextHolder.clearContext(); // 인증 정보 제거
        disableMemberRepository.delete(disabledMember);
        log.info("Disabled user detected and removed from the context.");
    }
}
