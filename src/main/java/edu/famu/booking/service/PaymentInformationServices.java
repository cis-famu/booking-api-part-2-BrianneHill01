package edu.famu.booking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.booking.models.paymentInformation;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PaymentInformationServices {
    private Firestore firestore;

    public PaymentInformationServices(){
        this.firestore = FirestoreClient.getFirestore();
    }

    public paymentInformation getPaymentInformation(DocumentReference docRef) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.toObject(paymentInformation.class);
    }
}
