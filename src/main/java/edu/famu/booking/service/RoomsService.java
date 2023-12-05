package edu.famu.booking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.util.Timestamps;
import edu.famu.booking.models.Rooms;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class RoomsService {
    private Firestore firestore;

    public RoomsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Rooms documentSnapshotToRooms(DocumentSnapshot document) {
        Rooms rooms = null;
        if (document.exists()) {
            ArrayList<String> images = (ArrayList<String>) document.get("images");
            rooms = new Rooms(document.getId(), document.getString("hotelID"), document.getString("roomType"), document.getDouble("price"), document.getDouble("capacity").intValue(), document.getString("description"), document.getString("availability"), images, document.getTimestamp("createdAt"));
        }
        return rooms;

    }

    public ArrayList<Rooms> getAllRooms() throws ExecutionException, InterruptedException {
        CollectionReference roomsCollection = firestore.collection("Rooms");
        ApiFuture<QuerySnapshot> future = roomsCollection.get();

        ArrayList<Rooms> roomsList = new ArrayList<>();

        for (DocumentSnapshot document : future.get().getDocuments()) {
            Rooms rooms = documentSnapshotToRooms(document);
            if (rooms != null)
                roomsList.add(rooms);
        }
        return roomsList;
    }

    public Rooms getRoomsById(String roomID) throws ExecutionException, InterruptedException {
        CollectionReference roomsCollection = firestore.collection("Rooms");
        ApiFuture<DocumentSnapshot> future = roomsCollection.document(roomID).get();
        DocumentSnapshot document = future.get();

        return documentSnapshotToRooms(document);


    }

    public String createRooms(Rooms rooms) throws ExecutionException, InterruptedException {

        String roomsId = null;
        ApiFuture<DocumentReference> future = firestore.collection("Rooms").add(rooms);
        DocumentReference postRef = future.get();
        roomsId = postRef.getId();

        return roomsId;
    }

    public void updateRooms(String id, Map<String, String> updatedValues) throws ParseException {
        String[] allowed = {"createdAt"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for (Map.Entry<String, String> entry : updatedValues.entrySet()) {
            String key = entry.getKey();
            if (list.contains(key))
                formattedValues.put(key, entry.getValue());
        }

        DocumentReference roomsDoc = firestore.collection("Rooms").document(id);
        if (roomsDoc != null)
            roomsDoc.update(formattedValues);

    }
    public void sellRoom(String roomsId, String roomType, int numberOfRooms) throws ExecutionException, InterruptedException {
        DocumentReference roomsDoc = firestore.collection("Room").document(roomsId);

        roomsDoc.update("available" + roomType, FieldValue.increment(-numberOfRooms));
    }

    public void returnRoom(String roomsId, String roomType, int numberOfRooms) throws ExecutionException, InterruptedException {
        DocumentReference roomsDoc = firestore.collection("Rooms").document(roomsId);

        roomsDoc.update("available" + roomType, FieldValue.increment(numberOfRooms));
    }
}


