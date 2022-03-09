package onlinepay.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Bill {
    private Integer companyId;
    private String billId;
    private Double billAmount;
    private String dueDate;
}
