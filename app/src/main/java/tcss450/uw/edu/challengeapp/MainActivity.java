package tcss450.uw.edu.challengeapp;

import android.net.Uri;
import android.net.nsd.NsdManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Login.OnFragmentInteractionListener,Registration.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new Login())
                        .commit();
            }
        }
      }


    @Override
    public void registerOpen() {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new Registration())
                .addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void loggingSuccess(String username, String password) {
        Display disp = new Display();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.username),username);
        args.putSerializable(getString(R.string.password),password);
        disp.setArguments(args);



        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, disp);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void register(String username,String password) {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }


        Display disp = new Display();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.username),username);
        args.putSerializable(getString(R.string.password),password);
        disp.setArguments(args);


        if (findViewById(R.id.fragmentContainer) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, disp)
                    .commit();
        }
    }
}
