package ggomg.MemberManagement.domain.disabledMember;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "disabledMember", timeToLive = 600)
public class DisabledMember {

    @Id
    private String id;

    @Indexed
    private String memberId;

    private DisabledMember(String memberId) {
        this.memberId = memberId;
    }

    public static DisabledMember of(String memberId) {
        return new DisabledMember(memberId);
    }
}
