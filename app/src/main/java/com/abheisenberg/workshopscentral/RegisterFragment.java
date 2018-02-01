package com.abheisenberg.workshopscentral;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.abheisenberg.workshopscentral.UserSharedPreferences.UserSharedPref;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private EditText et_nameR, et_emailR, et_pwR, et_phoneR;
    private Button bt_register;
    private UserSharedPref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register, container, false);

        pref = new UserSharedPref(getActivity());

        et_emailR = (EditText) rootView.findViewById(R.id.et_emailR);
        et_pwR = (EditText) rootView.findViewById(R.id.et_pwR);
        et_nameR = (EditText) rootView.findViewById(R.id.et_nameR);
        et_phoneR = (EditText) rootView.findViewById(R.id.et_phoneR);

        bt_register = (Button) rootView.findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

        if(view.getId() == R.id.bt_register){

            if(validateForm()){

                FragmentTransaction fTrans = getFragmentManager().beginTransaction();

                pref.createLoginSession(et_nameR.getText().toString(),
                        et_phoneR.getText().toString(),
                        et_emailR.getText().toString(),
                        et_pwR.getText().toString());

                fTrans.replace(R.id.fragment, new WelcomeFragment());
                fTrans.commit();
            }

        }
    }

    public boolean validateForm(){
        boolean isValid = true;

        if(TextUtils.isEmpty(et_emailR.getText().toString())){
            et_emailR.setError("Required");
            isValid = false;
        }
        if(TextUtils.isEmpty(et_phoneR.getText().toString())){
            et_phoneR.setError("Required");
            isValid = false;
        }
        if(TextUtils.isEmpty(et_nameR.getText().toString())){
            et_nameR.setError("Required");
            isValid = false;
        }
        if(TextUtils.isEmpty(et_pwR.getText().toString())){
            et_pwR.setError("Required");
            isValid = false;
        }


        return isValid;
    }

    public boolean isFragmentStackEmpty(){
        return getFragmentManager().getBackStackEntryCount() == 0;
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
