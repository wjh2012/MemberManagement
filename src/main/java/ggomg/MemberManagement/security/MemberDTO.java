package ggomg.MemberManagement.security;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    private String nickname;

}
