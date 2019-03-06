package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
class FirebaseConfig {
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("firebase-adminsdk.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://XXX.firebaseio.com/").build();

        FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance();
    }
}
