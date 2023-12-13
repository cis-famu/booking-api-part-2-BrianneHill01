package edu.famu.booking.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @DocumentId
    private @Nullable String userID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private @Nullable PaymentInformation pay;
    private @Nullable Timestamp createdAt;

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }

}

