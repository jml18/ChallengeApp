package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import tcss450.uw.edu.challengeapp.ChatActivities.ChatActivity;
import tcss450.uw.edu.challengeapp.model.Credentials;
import tcss450.uw.edu.challengeapp.utils.SendPostAsyncTask;

public class MainActivity extends AppCompatActivity implements Login.OnFragmentInteractionListener, Registration.OnFragmentInteractionListener, Display.OnSuccessFragmentInteractionListener {
    private Credentials mCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                SharedPreferences prefs =
                        getSharedPreferences(
                                getString(R.string.keys_shared_prefs),
                                Context.MODE_PRIVATE);

                if (prefs.getBoolean(getString(R.string.keys_prefs_stay_logged_in),
                        false)) {
                    //loadSuccessFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer, new Login(),
                                    getString(R.string.keys_fragment_login))
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer, new Login(),
                                    getString(R.string.keys_fragment_login))
                            .commit();
                }

            }
        }
    }


    @Override
    public void registerOpen() {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new Registration(),getString(R.string.keys_fragment_register))
                .addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onLoginAttempt(Credentials credentials) {
        //build the web service URL
        Uri uri = new Uri.Builder()
                .scheme("https")
                .appendPath(getString(R.string.ep_base_url))
                .appendPath(getString(R.string.ep_login))
                .build();

        //build the JSONObject
        JSONObject msg = credentials.asJSONObject();

        mCredentials = credentials;

        //instantiate and execute the AsyncTask.
        //Feel free to add a handler for onPreExecution so that a progress bar
        //is displayed or maybe disable buttons. You would need a method in
        //LoginFragment to perform this.
        new SendPostAsyncTask.Builder(uri.toString(), msg)
                .onPostExecute(this::handleLoginOnPost)
                .onCancelled(this::handleErrorsInTask)
                .build().execute();
    }


    @Override
    public void register(Credentials credentials) {
        //build the web service URL
        Uri uri = new Uri.Builder()
                .scheme("https")
                .appendPath(getString(R.string.ep_base_url))
                .appendPath(getString(R.string.ep_register))
                .build();

        //build the JSONObject
        JSONObject msg = credentials.asJSONObject();

        mCredentials = credentials;

        //instantiate and execute the AsyncTask.
        //Feel free to add a handler for onPreExecution so that a progress bar
        //is displayed or maybe disable buttons. You would need a method in
        //LoginFragment to perform this.
        new SendPostAsyncTask.Builder(uri.toString(), msg)
                .onPostExecute(this::handleRegisterOnPost)
                .onCancelled(this::handleErrorsInTask)
                .build().execute();
    }

    private void checkStayLoggedIn() {
        SharedPreferences prefs =
            getSharedPreferences(
                    getString(R.string.keys_shared_prefs),
                    Context.MODE_PRIVATE);
        //save the username for later usage
        prefs.edit().putString(
                getString(R.string.keys_prefs_username),
                mCredentials.getUsername())
                .apply();
        if (((CheckBox) findViewById(R.id.logCheckBox)).isChecked()) {
            //save the users “want” to stay logged in
            prefs.edit().putBoolean(
                    getString(R.string.keys_prefs_stay_logged_in),
                    true)
                    .apply();
        }
    }

    @Override
    public void onLogout() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        prefs.edit().remove(getString(R.string.keys_prefs_username));

        prefs.edit().putBoolean(
                getString(R.string.keys_prefs_stay_logged_in),
                false)
                .apply();
        //the way to close an app programmaticaly
        finishAndRemoveTask();
    }

    private void loadSuccessFragment() {
        //loading frag
//        Display successFragment = new Display();
//        getSupportFragmentManager().popBackStack();
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmentContainer, successFragment);
//
//        // Commit the transaction
//        transaction.commit();

        //ChatActivity
        Intent intent = new Intent(this, ChatActivity.class);
        ActivityCompat.finishAffinity(this);
        startActivity(intent);
    }

    //Async methods

    /**
     * Handle errors that may occur during the AsyncTask.
     *
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNCT_TASK_ERROR", result);
    }

    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     *
     * @param result the JSON formatted String response from the web service
     */
    private void handleLoginOnPost(String result) {
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            if (success) {
                //Login was successful. Switch to the loadSuccessFragment.
                checkStayLoggedIn();
                loadSuccessFragment();
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                Login frag =
                        (Login) getSupportFragmentManager()
                                .findFragmentByTag(
                                        getString(R.string.keys_fragment_login));
                frag.setError("Log in unsuccessful");
            }

        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            //or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR", result
                    + System.lineSeparator()
                    + e.getMessage());
        }
    }

    /**
     *
     */
    private void handleRegisterOnPost(String result){
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            if (success) {
                getSupportFragmentManager().popBackStack();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new Login(),getString(R.string.keys_fragment_login));

                // Commit the transaction
                transaction.commit();
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                Registration frag =
                        (Registration) getSupportFragmentManager()
                                .findFragmentByTag(
                                        getString(R.string.keys_fragment_register));
                frag.setError("Registration is unsuccessful");
            }

        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            //or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR", result
                    + System.lineSeparator()
                    + e.getMessage());
        }
    }


}
