package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private void register(View view) {
        boolean noError = true;
        String username = ((TextView) getActivity().findViewById(R.id.regUserTxt)).getText().toString();
        String password1 = ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).getText().toString();
        String password2 = ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).getText().toString();
        String email = ((TextView) getActivity().findViewById(R.id.regEmailText)).getText().toString();
        String firstName = ((TextView) getActivity().findViewById(R.id.regFirstNameText)).getText().toString();
        String lastName = ((TextView) getActivity().findViewById(R.id.regLastNameText)).getText().toString();
        if (username.isEmpty()) {
            ((TextView) getActivity().findViewById(R.id.regUserTxt)).setError("Please enter username");
            noError = false;
        }
        if (password1.isEmpty()) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter password");
            noError = false;
        }
        if (password2.isEmpty()) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter password");
            noError = false;
        }
        if (password1.length() < 6 && password2.length() < 6 && noError) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter password with 6 characters or more");
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter password with 6 characters or more");
            noError = false;
        }
        if (!password1.equals(password2) && noError) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter matching password");
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter matching password");
            noError = false;

        }
        if (noError) {
            Editable pwd = (Editable) ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).getText();
            mListener.register(new Credentials.Builder(username, pwd)
                    .addEmail(email)
                    .addFirstName(firstName)
                    .addLastName(lastName)
                    .build());
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
