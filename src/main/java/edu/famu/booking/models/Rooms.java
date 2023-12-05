package edu.famu.booking.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.util.Timestamps;

import java.text.ParseException;
import java.util.ArrayList;

public class Rooms {
    @DocumentId
    private @Nullable String roomID;
    private String hotelID;
    private String roomType;
    private double price;
    private int capacity;
    private String description;
    private String availability;
    private ArrayList<String> images;
    private @Nullable Timestamp createdAt;

    public Rooms(String roomID, String hotelID, String roomType, double price, int capacity, String description, String availability, ArrayList<String> images, Timestamp createdAt) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.roomType = roomType;
        this.price = price;
        this.capacity = capacity;
        this.description = description;
        this.availability = availability;
        this.images = images;
        this.createdAt = createdAt;
    }

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }
}
