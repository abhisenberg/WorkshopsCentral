package com.abheisenberg.workshopscentral;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SigninFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SigninFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    public SigninFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private FragmentTransaction fTrans;
    private Button bt_signin, bt_createAccount;
    private EditText et_pw, et_email;
    private UserSharedPref pref;
    private String email, pw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signin_screen, container, false);

        pref = new UserSharedPref(getActivity());

        fTrans = getFragmentManager().beginTransaction();

        et_email = (EditText) rootView.findViewById(R.id.et_email);
        et_pw = (EditText) rootView.findViewById(R.id.et_pw);
        bt_createAccount = (Button) rootView.findViewById(R.id.bt_noAccount);
        bt_signin = (Button) rootView.findViewById(R.id.bt_signin);

        bt_signin.setOnClickListener(this);
        bt_createAccount.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();

        fTrans = getFragmentManager().beginTransaction();

        switch(id){
            case R.id.bt_signin:
               if(validateForm()){
                   if(pref.checkAccount(et_email.getText().toString(), et_pw.getText().toString())){
                       pref.login();
                   } else {
                       Toast.makeText(getActivity(), "Please register first!", Toast.LENGTH_SHORT).show();
                       fTrans.replace(R.id.fragment, new RegisterFragment()).commit();
                   }
                   getFragmentManager().popBackStack();

               }
               break;
            case R.id.bt_noAccount:
                fTrans.replace(R.id.fragment, new RegisterFragment());
                fTrans.commit();
                break;
        }
    }

    public boolean validateForm(){
        boolean isValid = true;

        if(TextUtils.isEmpty(et_email.getText().toString())){
            et_email.setError("Required");
            isValid = false;
        }
        if(TextUtils.isEmpty(et_pw.getText().toString())){
            et_pw.setError("Required");
            isValid = false;
        }
        return isValid;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
