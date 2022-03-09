package onlinepay.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Bill {
    private Long companyId;
    private Long billId;
    private Double billAmount;
    private LocalDate dueDate;
}
