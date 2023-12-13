package edu.famu.booking.models;

import com.google.cloud.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.text.ParseException;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reviews {
    private @Nullable String reviewID;
    private String hotelID;
    private String userID;
    private long rating;
    private String	comment;
    private Timestamp date;
    private @Nullable Timestamp createdAt;

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}