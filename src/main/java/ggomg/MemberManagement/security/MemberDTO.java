package ggomg.MemberManagement.security;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements Serializable {

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    private String nickname;

}
