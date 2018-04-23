package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Display.OnSuccessFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Display extends Fragment {

    private OnSuccessFragmentInteractionListener mListener;

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


        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        if (prefs.getBoolean(getString(R.string.keys_prefs_stay_logged_in), false)) {
            getView().findViewById(R.id.successLogoutButton)
                    .setOnClickListener(v -> mListener.onLogout());
        } else {
            getView().findViewById(R.id.successLogoutButton).setVisibility(View.GONE);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSuccessFragmentInteractionListener) {
            mListener = (OnSuccessFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSuccessFragmentInteractionListener {
        void onLogout();
    }




}
