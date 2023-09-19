package ggomg.MemberManagement.domain.member.DTO;

import lombok.Data;

@Data
public class OffsetPagingRequest {

    // 요청 페이지
    private int page;
    // 페이지 당 데이터
    private int pageSize;

    public OffsetPagingRequest() {
        this.page = 1;
        this.pageSize = 10;
    }
}
