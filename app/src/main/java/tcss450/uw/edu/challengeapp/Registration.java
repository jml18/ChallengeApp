package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        String username = ((TextView) getActivity().findViewById(R.id.regUserTxt)).getText().toString();
        String password1 = ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).getText().toString();
        String password2 = ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).getText().toString();
        if (username.isEmpty()) {
            ((TextView) getActivity().findViewById(R.id.regUserTxt)).setError("Please enter username");
        }
        if (password1.isEmpty() && password2.isEmpty()) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter password");
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter password");
            return;
        }
        if (password1.length()<6 && password2.length()<6) {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter password with 6 characters or more");
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter password with 6 characters or more");
            return;
        }
        if (password1.equals(password2) && !username.isEmpty()) mListener.register(username,password1);
        else {
            ((TextView) getActivity().findViewById(R.id.regPwdTxt1)).setError("Please enter matching password");
            ((TextView) getActivity().findViewById(R.id.regPwdTxt2)).setError("Please enter matching password");

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
        void register(String username,String password);
    }
}
