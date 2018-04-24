package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.text.Editable;

import org.w3c.dom.Text;

import tcss450.uw.edu.challengeapp.model.Credentials;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Login extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Button b = (Button) v.findViewById(R.id.registerButton);
        b.setOnClickListener(this::register);
        b = (Button) v.findViewById(R.id.loginButton);
        b.setOnClickListener(this::login);
        return v;

    }

    private void register(View view) {
        if (mListener != null) {
            mListener.registerOpen();
        }
    }

    private void login(View view) {
        TextView username = (TextView) getActivity().findViewById(R.id.logUserTxt);
        TextView password = (TextView) getActivity().findViewById(R.id.logPwdTxt);
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            if (username.getText().toString().isEmpty())
                username.setError("Please enter username");
            if (password.getText().toString().isEmpty())
                password.setError("Please enter password");
        } else if (mListener != null) {
            //fix this to use credential class
            mListener.onLoginAttempt(new Credentials.Builder(username.getText().toString(), (Editable) password.getText()).build());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * Allows an external source to set an error message on this fragment. This may
     * be needed if an Activity includes processing that could cause login to fail.
     *
     * @param err the error message to display.
     */
    public void setError(String err) {
        //Log in unsuccessful for reason: err. Try again.
        //you may want to add error stuffs for the user here.
        ((TextView) getActivity().findViewById(R.id.logUserTxt)).setError(err);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void registerOpen();
        void onLoginAttempt(Credentials info);
    }
}
