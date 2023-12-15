package edu.famu.booking.service;

import edu.famu.booking.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.User;
import edu.famu.booking.models.PaymentInformation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class UserService {
    private Firestore firestore;

    public UserService(){
        this.firestore = FirestoreClient.getFirestore();

    }

    private User documentSnapshotToUsers(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        User user = null;
        if (document.exists()) {
            PaymentInformationService paymentInformationService = new PaymentInformationService();
            PaymentInformation paymentInformation = paymentInformationService.getPaymentInformation((DocumentReference) document.get("paymentInformation"));
            user = new User(document.getId(), document.getString("name"), document.getString("email"), document.getString("phone"), document.getString("address"),paymentInformation, document.getTimestamp("createdAt"));
        }
        return user;

    }

    public ArrayList<User> getAllUsers()throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("Users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();

        ArrayList<User> userssList = new ArrayList<>();

        for(DocumentSnapshot document: future.get().getDocuments()){
            User user = documentSnapshotToUsers(document);
            if(user != null)
                userssList.add(user);
        }
        return userssList;

    }

    public User getUsersById(String userID) throws ExecutionException, InterruptedException {
        CollectionReference userssCollection = firestore.collection("Users");
        ApiFuture<DocumentSnapshot> future = userssCollection.document(userID).get();
        DocumentSnapshot document = future.get();
        return documentSnapshotToUsers(document);
    }
    public ArrayList<User> getUserssBycreatedAtAndSort(String sort) throws ExecutionException, InterruptedException {
        ArrayList<User> userss = new ArrayList<>();

        CollectionReference usersCollection = firestore.collection("Users");

        // Default sorting is ascending
        Query.Direction direction = Query.Direction.ASCENDING;
        if ("desc".equalsIgnoreCase(sort)) {
            direction = Query.Direction.DESCENDING;
        }

        // Order the query by createdAt field based on the provided direction
        Query query = usersCollection.orderBy("createdAt", direction);

        ApiFuture<QuerySnapshot> future = query.get();

        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            User user = documentSnapshotToUsers(document);
            if (user != null) {
                userss.add(user);
            }
        }
        return userss;
    }
    public String createUser(User user) throws ExecutionException, InterruptedException {
        String userId = null;

// Step 1: Create a new document for the user
            ApiFuture<DocumentReference> userFuture = firestore.collection("Users").add(user);
            DocumentReference userRef = userFuture.get();
            userId = userRef.getId();

// Step 2: Get the user document data
            ApiFuture<DocumentSnapshot> userDocumentFuture = userRef.get();
            DocumentSnapshot userDocument = userDocumentFuture.get();
            if (userDocument.exists()) {
                // Step 3: Get the paymentInformation field from the user document
                Object paymentInfoObj = userDocument.get("pay");
                if (paymentInfoObj instanceof DocumentReference) {
                    DocumentReference paymentInfoRef = (DocumentReference) paymentInfoObj;

                    // Step 4: Get the paymentInformation data
                    ApiFuture<DocumentSnapshot> paymentInfoSnapshotFuture = paymentInfoRef.get();
                    DocumentSnapshot paymentInfoDocument = paymentInfoSnapshotFuture.get();

                    // Step 5: Assuming you want to create a new document for PaymentInformation
                    if (paymentInfoDocument.exists()) {
                        PaymentInformation paymentInformation = paymentInfoDocument.toObject(PaymentInformation.class);

                        // Assuming PaymentInformation is a separate collection
                        ApiFuture<DocumentReference> paymentInfoFuture = firestore.collection("PaymentInformation").add(paymentInformation);
                        DocumentReference paymentInfoNewRef = paymentInfoFuture.get();

                        // Step 6: Update the user document with the new paymentInformation reference
                        userRef.update("pay", paymentInfoNewRef);
                    }
                } else {
                    // Handle the case where "pay" is not a DocumentReference
                    // You might want to log an error or throw an exception
                    System.err.println("'pay' field is not a DocumentReference");
                }
            }


        return userId;
    }

    public void updateUsers(String id, Map<String, String> updatedValues){
        String[] allowed = {"name", "email", "address"};
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
        DocumentReference usersDoc = firestore.collection("Users").document(id);
        if(usersDoc != null)
            usersDoc.update(formattedValues);
    }
    public void deleteUser(String roomID) throws ExecutionException, InterruptedException {
        CollectionReference userssCollection = firestore.collection("Users");
        ApiFuture<DocumentSnapshot> future = userssCollection.document(roomID).get();
        DocumentSnapshot document = future.get();
        if(document.getId().equals(roomID)){
            userssCollection.document(roomID).delete();
        }
    }
}