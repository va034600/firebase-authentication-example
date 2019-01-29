import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream serviceAccount = ClassLoader.getSystemResourceAsStream("serviceAccountKey.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://XXX.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
        String idToken = "<>";
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        System.out.println("Successfully fetched user data: " + userRecord.getUid());
    }
}


