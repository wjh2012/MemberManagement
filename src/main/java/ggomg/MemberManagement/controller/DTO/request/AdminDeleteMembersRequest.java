package ggomg.MemberManagement.controller.DTO.request;

import java.util.List;
import lombok.Data;

@Data
public class AdminDeleteMembersRequest {
    private List<Long> selectedIds;
}
