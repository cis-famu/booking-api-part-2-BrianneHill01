package edu.famu.booking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.Hotel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
@Service
public class HotelService {
    private Firestore firestore;

    public HotelService(){
        this.firestore = FirestoreClient.getFirestore();
    }

    private Hotel documentSnapshotToHotels(DocumentSnapshot document)
    {
        Hotel hotels = null;
        if(document.exists()){
            ArrayList<String> amenities = (ArrayList<String>) document.get("amenities");
            hotels = new Hotel(document.getId(),document.getString("name"),document.getString("description"),document.getDouble("rating"),document.getString("address"), document.getString("contactInformation"),amenities, document.getTimestamp("createdAt"));
        }
        return hotels;

    }
    public ArrayList<Hotel> getAllHotels() throws ExecutionException, InterruptedException {
        CollectionReference hotelsCollection = firestore.collection("Hotels");
        ApiFuture<QuerySnapshot> future = hotelsCollection.get();

        ArrayList<Hotel> hotelsList = new ArrayList<>();

        for(DocumentSnapshot document: future.get().getDocuments())
        {
            Hotel hotels = documentSnapshotToHotels(document);
            if(hotels != null)
                hotelsList.add(hotels);
        }
        return hotelsList;
    }

    public Hotel getHotelsById(String hotelID) throws ExecutionException, InterruptedException {
        CollectionReference hotelsCollection = firestore.collection("Hotels");
        ApiFuture<DocumentSnapshot> future = hotelsCollection.document(hotelID).get();
        DocumentSnapshot document = future.get();

        return documentSnapshotToHotels(document);
    }


}

