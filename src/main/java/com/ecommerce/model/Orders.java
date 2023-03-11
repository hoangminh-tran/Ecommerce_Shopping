package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_Id")
    private Long Order_Id;

    @Temporal(TemporalType.DATE)
    private Date OrdDate;

    @Temporal(TemporalType.DATE)
    private Date ShipDate;

    private int Order_Status;
    // 1 Completed
    // 2 Processed
    // 3 Cancelled

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "User_id")
    private User user;

    @OneToMany(mappedBy = "orders")
    private List<OrderDetails> orderDetailsList;

    @OneToOne(mappedBy = "orders")
    private Payment payment;

    public Orders(Date ordDate, Date shipDate, int order_Status, User user) {
        OrdDate = ordDate;
        ShipDate = shipDate;
        Order_Status = order_Status;
        this.user = user;
    }
}
