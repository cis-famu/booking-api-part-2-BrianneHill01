package edu.famu.booking.models;

import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class paymentInformation {
    private String cardNumber;
    private String expirationDate;
    private String billingAddress;
    private Timestamp createdAt;

}

