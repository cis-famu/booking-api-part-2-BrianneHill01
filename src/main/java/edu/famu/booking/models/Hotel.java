package edu.famu.booking.models;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Hotel {
    @DocumentId
    private @Nullable String hotelID;
    private String name;
    private String description;
    private long rating;
    private String address;
    private String contactInformation;
    private ArrayList<String> amenities;
    private @Nullable Timestamp createdAt;

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}