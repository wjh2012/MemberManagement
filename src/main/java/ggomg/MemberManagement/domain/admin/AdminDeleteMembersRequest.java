package ggomg.MemberManagement.domain.admin;

import java.util.List;
import lombok.Data;

@Data
public class AdminDeleteMembersRequest {
    private List<Long> selectedIds;
}
