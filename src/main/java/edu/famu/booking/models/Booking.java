package edu.famu.booking.model;

import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private @Nullable String bookingID;
    private String userID;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private float totalPrice;
    private String status;
    private String paymentStatus;
    private Timestamp createdAt;

    public Booking(String book,Timestamp checkIn, Timestamp checkOut,String payment,float total, String progress, String uID){
        bookingID = book;
        checkInDate = checkIn;
        checkOutDate= checkOut;
        paymentStatus = payment;
        totalPrice = total;
        status = progress;
        userID = uID;
    }


}