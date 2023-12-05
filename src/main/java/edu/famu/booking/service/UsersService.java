package edu.famu.booking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.paymentInformation;
import edu.famu.booking.models.Users;
import edu.famu.booking.models.paymentInformation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UsersService {
    private Firestore firestore;

    public UsersService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Users documentSnapshotToUsers(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        Users users = null;
        if (document.exists()) {
            PaymentInformationServices paymentInformationService = new PaymentInformationServices();
            paymentInformation paymentInformation = paymentInformationService.getPaymentInformation((DocumentReference) document.get("paymentInformation"));
            users = new Users(document.getId(), document.getString("name"), document.getString("email"), document.getString("phone"), paymentInformation, document.getTimestamp("createdAt"));
        }
        return users;

    }

    public ArrayList<Users> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("Users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();

        ArrayList<Users> usersList = new ArrayList<>();

        for (DocumentSnapshot document : future.get().getDocuments()) {
            Users users = documentSnapshotToUsers(document);
            if (users != null)
                usersList.add(users);
        }
        return usersList;
    }

    public Users getUsersById(String userID) throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("Users");
        ApiFuture<DocumentSnapshot> future = usersCollection.document(userID).get();
        DocumentSnapshot document = future.get();

        return documentSnapshotToUsers(document);
    }


    public String createUser(Users users) throws ExecutionException, InterruptedException {
        String userId = null;

        ApiFuture<DocumentReference> future = firestore.collection("User").add(users);
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