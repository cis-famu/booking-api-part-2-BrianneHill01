package edu.famu.booking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.PaymentInformation;
import edu.famu.booking.models.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private Firestore firestore;

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private User documentSnapshotToUsers(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        User user = null;
        if (document.exists()) {
            PaymentInformationService paymentInformationService = new PaymentInformationService();
            PaymentInformation paymentInformation = paymentInformationService.getPaymentInformation((DocumentReference) document.get("paymentInformation"));
            user = new User(document.getId(), document.getString("name"), document.getString("email"), document.getString("phone"), document.getString("address"), paymentInformation, document.getTimestamp("createdAt"));
        }
        return user;

    }

    public ArrayList<User> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("Users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();

        ArrayList<User> userList = new ArrayList<>();

        for (DocumentSnapshot document : future.get().getDocuments()) {
            User user = documentSnapshotToUsers(document);
            if (user != null)
                userList.add(user);
        }
        return userList;
    }

    public User getUsersById(String userID) throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("Users");
        ApiFuture<DocumentSnapshot> future = usersCollection.document(userID).get();
        DocumentSnapshot document = future.get();

        return documentSnapshotToUsers(document);
    }


    public String createUser(User user) throws ExecutionException, InterruptedException {
        String userId = null;

        ApiFuture<DocumentReference> future = firestore.collection("User").add(user);
        DocumentReference postRef = future.get();
        userId = postRef.getId();

        return userId;
    }

    public void updateUsers(String id, Map<String, String> updateValues){

        String [] allowed = {"firstName", "lastName", "phone", "address", "createdAt"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for(Map.Entry<String, String> entry : updateValues.entrySet()) {
            String key = entry.getKey();
            if(list.contains(key))
                formattedValues.put(key, entry.getValue());
        }

        DocumentReference passengerDoc = firestore.collection("Users").document(id);
        passengerDoc.update(formattedValues);
    }


}