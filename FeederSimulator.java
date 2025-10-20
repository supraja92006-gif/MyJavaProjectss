import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class FeederSimulator {

    public static void main(String[] args) throws Exception {

        FileInputStream serviceAccount =
                new FileInputStream("firebase-config/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://YOUR_DATABASE_URL.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference commandRef = database.getReference("devices/petfeeder01/commands/latest");
        DatabaseReference statusRef = database.getReference("devices/petfeeder01/status");
        DatabaseReference logRef = database.getReference("devices/petfeeder01/logs");

        System.out.println("✅ Feeder Simulator Started...");
        System.out.println("Listening for commands...");

        commandRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String action = snapshot.child("action").getValue(String.class);
                    Long grams = snapshot.child("grams").getValue(Long.class);
                    if (action == null || grams == null) return;

                    System.out.println("Command received: " + action + " " + grams + "g");

                    if (action.equals("dispense")) {
                        try {
                            statusRef.child("state").setValueAsync("dispensing");
                            Thread.sleep(3000); // simulate delay
                            statusRef.child("state").setValueAsync("completed");
                            statusRef.child("dispensed").setValueAsync(grams);

                            // Log the action
                            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            Map<String, Object> log = new HashMap<>();
                            log.put("time", time);
                            log.put("grams", grams);
                            log.put("status", "completed");

                            logRef.push().setValueAsync(log);

                            System.out.println("✅ Dispensed " + grams + " grams at " + time);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Firebase error: " + error.getMessage());
            }
        });

        // Keep the app running
        new Scanner(System.in).nextLine();
    }
}

