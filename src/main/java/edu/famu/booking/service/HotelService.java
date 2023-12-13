package edu.famu.booking.service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.Hotel;
import edu.famu.booking.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class HotelService {
    private Firestore firestore;

    public HotelService(){
        this.firestore = FirestoreClient.getFirestore();

    }

    private Hotel documentSnapshotToHotel(DocumentSnapshot document)
    {
        Hotel hotel = null;
        if(document.exists()){
            ArrayList<String> amenities = (ArrayList<String>) document.get("amenities");
            hotel = new Hotel(document.getId(), document.getString("name"), document.getString("description"), document.getLong("rating"), document.getString("address"), document.getString("contactInformation"),amenities,document.getTimestamp("createdAt"));
        }
        return hotel;

    }
    public ArrayList<Hotel> getAllHotels()throws ExecutionException, InterruptedException {
        CollectionReference hotelsCollection = firestore.collection("hotel");
        ApiFuture<QuerySnapshot> future = hotelsCollection.get();

        ArrayList<Hotel> bookingsList = new ArrayList<>();

        for(DocumentSnapshot document: future.get().getDocuments()){
            Hotel hotel = documentSnapshotToHotel(document);
            if(hotel != null)
                bookingsList.add(hotel);
        }
        return bookingsList;

    }

    public Hotel getHotelsById(String hotelID) throws ExecutionException, InterruptedException {
        CollectionReference bookingsCollection = firestore.collection("hotel");
        ApiFuture<DocumentSnapshot> future = bookingsCollection.document(hotelID).get();
        DocumentSnapshot document = future.get();
        return documentSnapshotToHotel(document);
    }
    public String createHotels(Hotel hotel) throws ExecutionException, InterruptedException {
        String hotelsId = null;
        ApiFuture<DocumentReference> future = firestore.collection("hotel").add(hotel);
        DocumentReference postRef = future.get();
        hotelsId = postRef.getId();
        return hotelsId;

    }
    public void updateHotels(String id, Map<String, String> updatedValues){
        String[] allowed = {"name", "description", "address"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for(Map.Entry<String, String> entry : updatedValues.entrySet())
        {
            String key = entry.getKey();
            if(list.contains(key))
            {
                formattedValues.put(key, entry.getValue());
            }
        }
        DocumentReference hotelsDoc = firestore.collection("hotel").document(id);
        if(hotelsDoc != null)
            hotelsDoc.update(formattedValues);
    }
    public void deleteHotel(String hotelID) throws ExecutionException, InterruptedException {
        CollectionReference hotelsCollection = firestore.collection("Users");
        ApiFuture<DocumentSnapshot> future = hotelsCollection.document(hotelID).get();
        DocumentSnapshot document = future.get();
        if (document.getId().equals(hotelID)) {
            hotelsCollection.document(hotelID).delete();
        }

    }

}