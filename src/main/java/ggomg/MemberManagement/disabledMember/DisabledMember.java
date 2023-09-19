package ggomg.MemberManagement.disabledMember;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "disabledMember", timeToLive = 600)
public class DisabledMember {

    @Indexed
    private final Long memberId;
    @Id
    private Long id;

    private DisabledMember(Long memberId) {
        this.memberId = memberId;
    }

    public static DisabledMember of(Long memberId) {
        return new DisabledMember(memberId);
    }
}
