package ggomg.MemberManagement.API.member;

import lombok.Data;

@Data
public class APIMemberDTO {

    private Long id;
    private String name;
    private String username;
    private String email;

    public APIMemberDTO(Long id, String nickname, String username) {
        this.id=id;
        this.name=nickname;
        this.username=username;
        this.email = "tmp@tmp.com";
    }
}
