package ggomg.MemberManagement.domain.member.DTO;

import lombok.Data;

@Data
public class MemberSearchRequest {

    private OffsetPagingRequest offsetPagingRequest;
    private MemberSearchCondition memberSearchCondition;

}
