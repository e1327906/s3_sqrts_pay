package com.qre.pay.entity.payment;

import com.qre.pay.entity.base.AbstractPersistableEntity;
import com.qre.pay.entity.base.DbFieldName;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DbFieldName.PAYMENT_TXN)
public class PaymentTransaction extends AbstractPersistableEntity<UUID> {
    // Status Constants
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = DbFieldName.TXN_ID)
    private UUID txnId;

    @Column(name = DbFieldName.TXN_DATETIME)
    @Temporal(TemporalType.TIMESTAMP)
    Date txnDatetime;

    @Column(name = DbFieldName.TXN_STATUS)
    private int txnStatus;

    @Column(name = DbFieldName.EMAIL)
    private String email;

    @Column(name = DbFieldName.PAYMENT_REF_NO)
    private String paymentRefNo;

    @Column(name = DbFieldName.AMOUNT)
    private Long amount;

    @Column(name = DbFieldName.CURRENCY)
    private String currency;

    @Override
    public UUID getId() {
        return txnId;
    }
}
