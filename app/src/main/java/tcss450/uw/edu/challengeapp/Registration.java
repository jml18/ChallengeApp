package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tcss450.uw.edu.challengeapp.model.Credentials;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registration.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Registration extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        Button b = v.findViewById(R.id.registerButton);
        b.setOnClickListener(this::register);
        return v;
    }

    private void register(View v) {
        if (mListener != null) {
            EditText email = getActivity().findViewById(R.id.regEmailText);
            EditText firstName = getActivity().findViewById(R.id.regFirstNameText);
            EditText lastName = getActivity().findViewById(R.id.regLastNameText);

            EditText username = getActivity().findViewById(R.id.regUserTxt);
            EditText password = getActivity().findViewById(R.id.regPwdTxt1);
            EditText passwordConfirm = getActivity().findViewById(R.id.regPwdTxt2);

            String emailText = email.getText().toString().trim();
            String firstNameText = firstName.getText().toString().trim();
            String lastNameText = lastName.getText().toString().trim();

            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String passwordConfirmText = passwordConfirm.getText().toString().trim();

            int indexOfAt = emailText.indexOf('@');
            if (indexOfAt < 0 && indexOfAt > emailText.length() - 1) {
                email.setError("Invalid Email!!");
            } else if (emailText.equals("")) {
                email.setError("Email cannot be empty!");
            } else if (firstNameText.equals("")) {
                firstName.setError("First name cannot be empty!");
            } else if (lastNameText.equals("")) {
                lastName.setError("Last name cannot be empty!");
            } else if (usernameText.equals("")) {
                username.setError("Username cannot be empty");
            } else if (passwordText.equals("")) {
                password.setError("Password cannot be empty");
            } else if (passwordConfirmText.equals("")) {
                passwordConfirm.setError("Confirm Password cannot be empty");
            } else if (password.length() < 6){
                password.setError("Password must have at least 6 characters");
            } else if (!(passwordText.equals(passwordConfirmText))){
                password.setError("Password do not match!");
            } else {
                Credentials.Builder builder = new Credentials.Builder(usernameText, new SpannableStringBuilder(passwordText));
                mListener.register(builder.build());
            }
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
        ((TextView) getActivity().findViewById(R.id.regUserTxt)).setError(err);
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
        void register(Credentials credentials);
    }
}
