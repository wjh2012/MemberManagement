package ggomg.MemberManagement.controller.DTO.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PagingResponse<T> {

    private List<T> list = new ArrayList<>();
    private RecordInfo recordInfo;

}
