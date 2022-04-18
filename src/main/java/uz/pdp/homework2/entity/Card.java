package uz.pdp.homework2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.pdp.homework2.config.AuditingListener;
import uz.pdp.homework2.entity.enums.CardType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(value = AuditingListener.class)
public class Card {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @ManyToOne
    private Bank bank;

    @Column(nullable = false, length = 3)
    private String ccv;

    @Column(nullable = false)
    private Date expiredDate;

    @Column(nullable = false, length = 4)
    private String password;

    @Column(nullable = false)
    private String cardType;

    @CreatedBy
    private UUID cratedAt;

    @LastModifiedBy
    private UUID updatedAt;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private Timestamp creationTime;
}
