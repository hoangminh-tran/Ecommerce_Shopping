package com.ecommerce.dto;

import com.ecommerce.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long Order_Id;
    private Date OrdDate;

    private Date ShipDate;

    private String Order_Status;
    // 1 Completed
    // 2 Processed
    // 3 Cancelled

    private String email;

}
