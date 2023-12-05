package edu.famu.booking.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @DocumentId
    private @Nullable String userID;
    private String name;
    private String email;
    private String phone;
    private String address;

    private @Nullable paymentInformation paymentInformation;
    private @Nullable Timestamp createdAt;

    public Users(String id, String name, String email, String phone, edu.famu.booking.models.paymentInformation paymentInformation, Timestamp createdAt) {
    }

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}

