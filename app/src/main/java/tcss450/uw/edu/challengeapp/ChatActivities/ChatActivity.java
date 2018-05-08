package tcss450.uw.edu.challengeapp.ChatActivities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tcss450.uw.edu.challengeapp.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (findViewById(R.id.chatContainer) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chatContainer, new ChatFragment(),
                            getString(R.string.keys_fragment_chats))
                    .commit();
        }

    }
}

