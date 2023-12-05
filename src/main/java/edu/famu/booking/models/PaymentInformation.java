package edu.famu.booking.models;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {
    private String cardNumber;
    private String expirationDate;
    private String billingAddress;
    private Timestamp createdAt;
}

