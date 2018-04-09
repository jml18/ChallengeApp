package tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Login extends Fragment{

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

    private void login(View view){
        TextView username = (TextView) getActivity().findViewById(R.id.logUserTxt);
        TextView password = (TextView) getActivity().findViewById(R.id.logPwdTxt);

        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            if(username.getText().toString().isEmpty())
                username.setError("Please enter username");
            if(password.getText().toString().isEmpty())
                password.setError("Please enter password");
        }else if (mListener != null) {
            mListener.loggingSuccess(username.getText().toString(),password.getText().toString());
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
        void registerOpen();
        void loggingSuccess(String username,String password);
    }
}
