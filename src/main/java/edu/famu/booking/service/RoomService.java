package edu.famu.booking.service;
import edu.famu.booking.models.Hotel;
import edu.famu.booking.models.Rooms;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class RoomService {
    private Firestore firestore;

    public RoomService() {
        this.firestore = FirestoreClient.getFirestore();

    }

    private Rooms documentSnapshotToRoom(DocumentSnapshot document) {
        Rooms room = null;
        if (document.exists()) {
            ArrayList<String> images = (ArrayList<String>) document.get("images");
            room = new Rooms(document.getId(), document.getString("hotelID"), document.getString("roomType"), document.getLong("price"), document.getLong("capacity").intValue(), document.getString("description"), document.getString("availability"), images, document.getTimestamp("createdAt"));
        }
        return room;
    }

    public ArrayList<Rooms> getAllRooms() throws ExecutionException, InterruptedException {
        CollectionReference roomsCollection = firestore.collection("Rooms");
        ApiFuture<QuerySnapshot> future = roomsCollection.get();

        ArrayList<Rooms> roomsList = new ArrayList<>();

        for(DocumentSnapshot document: future.get().getDocuments())
        {
            Rooms rooms = documentSnapshotToRoom(document);
            if(rooms != null)
                roomsList.add(rooms);
        }
        return roomsList;

    }

    public Rooms getRoomsById(String roomID) throws ExecutionException, InterruptedException {
        CollectionReference bookingsCollection = firestore.collection("Rooms");
        ApiFuture<DocumentSnapshot> future = bookingsCollection.document(roomID).get();
        DocumentSnapshot document = future.get();
        return documentSnapshotToRoom(document);
    }

    public String createRooms(Rooms rooms) throws ExecutionException, InterruptedException {
        String roomsId = null;
        ApiFuture<DocumentReference> future = firestore.collection("Rooms").add(rooms);
        DocumentReference postRef = future.get();
        roomsId = postRef.getId();
        return roomsId;

    }

    public void updateRooms(String id, Map<String, String> updatedValues) {
        String[] allowed = {"roomType", "price", "description"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for (Map.Entry<String, String> entry : updatedValues.entrySet()) {
            String key = entry.getKey();
            if (list.contains(key)) {
                formattedValues.put(key, entry.getValue());
            }
        };//

        DocumentReference roomsDoc = firestore.collection("Rooms").document(id);
        if (roomsDoc != null)
            roomsDoc.update(formattedValues);
    }

    public void deleteRoom(String roomID) throws ExecutionException, InterruptedException {
        CollectionReference roomsCollection = firestore.collection("Rooms");
        ApiFuture<DocumentSnapshot> future = roomsCollection.document(roomID).get();
        DocumentSnapshot document = future.get();
        if (document.getId().equals(roomID)) {
            roomsCollection.document(roomID).delete();
        }

    }
}


