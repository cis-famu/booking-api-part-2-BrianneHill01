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
@AllArgsConstructor
@NoArgsConstructor

public class Rooms {
    @DocumentId
    private	@Nullable String roomID;
    private	String hotelID;
    private String roomType;
    private long price;
    private long capacity;
    private String	description;
    private String	availability;
    private ArrayList<String> images;
    private Timestamp createdAt;


    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}