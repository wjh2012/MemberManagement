package ggomg.MemberManagement.controller.DTO.request;

import ggomg.MemberManagement.member.DTO.MemberSearchCondition;
import lombok.Data;

@Data
public class MemberSearchRequest {

    private OffsetPagingRequest offsetPagingRequest;
    private MemberSearchCondition memberSearchCondition;

}
