package edu.famu.booking.models;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformation {

    @DocumentId
    private @Nullable String id;
    private String cardNumber;
    private String expirationDate;
    private String billingAddress;

}

