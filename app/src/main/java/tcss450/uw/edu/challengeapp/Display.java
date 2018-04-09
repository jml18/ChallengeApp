package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Display extends Fragment {

    public Display() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display, container, false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {

            String username = getArguments().getString(getString(R.string.username));
            String password = getArguments().getString(getString(R.string.password));
            updateDisp(username,password);

        }
    }

    private void updateDisp(String username , String password){
        TextView tv = getActivity().findViewById(R.id.dispUserTxt);
        tv.setText(username);
        tv = getActivity().findViewById(R.id.dispPwdTxt);
        tv.setText(password);
    }




}
