package com.qre.pay.entity.refund;

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
@Table(name = "refund_txn")
public class RefundTransaction extends AbstractPersistableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = DbFieldName.TXN_ID)
    private UUID txnId;

    @Column(name = DbFieldName.TXN_DATETIME)
    @Temporal(TemporalType.TIMESTAMP)
    Date txnDatetime;

    @Column(name = DbFieldName.SERIAL_NUMBER)
    private String serialNumber;

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
