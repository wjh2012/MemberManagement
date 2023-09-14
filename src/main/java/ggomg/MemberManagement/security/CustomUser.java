package ggomg.MemberManagement.security;

import java.util.Collection;
import java.util.Map;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
public class CustomUser implements UserDetails, OAuth2User, CredentialsContainer {

    private MemberDTO memberDTO;
    private Map<String, Object> attributes;

    public CustomUser(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberDTO.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.memberDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return this.memberDTO.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return true;
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
    public void eraseCredentials() {
        this.memberDTO.setPassword(null);
    }

    public String getNickname() {
        return this.memberDTO.getNickname();
    }

    @Override
    public String toString() {
        String sb = getClass().getName() + "["
                + "Username: [" + this.memberDTO.getUsername() + "], "
                + "Password=[PROTECTED], "
                + "Granted Authorities: [" + this.memberDTO.getAuthorities() + "], "
                + "User Attributes: [" + getAttributes() + "], "
                + "User Nickname: [" + this.memberDTO.getNickname() + "]"
                + "]";
        return sb;
    }
}
