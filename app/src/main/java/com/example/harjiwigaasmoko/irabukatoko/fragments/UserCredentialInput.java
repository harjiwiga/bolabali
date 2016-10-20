package com.example.harjiwigaasmoko.irabukatoko.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.harjiwigaasmoko.irabukatoko.R;
import com.example.harjiwigaasmoko.irabukatoko.additionalviews.SimpleAlertDialog;
import com.example.harjiwigaasmoko.irabukatoko.entity.User;
import com.example.harjiwigaasmoko.irabukatoko.handler.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserCredentialInput.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserCredentialInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCredentialInput extends android.support.v4.app.Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextIdType;
    private EditText editTextIdNum;
    private Activity activity;

    private DatabaseHandler dbHandler;

    private User guser;
    private Button saveButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCredentialInput.
     */
    // TODO: Rename and change types and number of parameters
    public static UserCredentialInput newInstance(String param1, String param2) {
        UserCredentialInput fragment = new UserCredentialInput();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserCredentialInput() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = getActivity();
//        activity.setTitle("User Input");
        dbHandler = DatabaseHandler.getInstance(activity);
//        editTextName = (EditText)findById
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("onCreateView", "onCreateView pass");
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_user_input, container, false);
        editTextName = (EditText) view.findViewById(R.id.editText);
        editTextEmail = (EditText) view.findViewById(R.id.editText2);
        editTextPhone = (EditText) view.findViewById(R.id.editText3);
        editTextAddress = (EditText) view.findViewById(R.id.editText4);
        editTextIdType = (EditText) view.findViewById(R.id.editText5);
        editTextIdNum = (EditText) view.findViewById(R.id.editText6);

        if (bundle != null) {
            guser = (User) bundle.getParcelable("user");
            editTextName.setText(guser.getName());
            editTextEmail.setText(guser.getEmail());
            editTextPhone.setText(guser.getPhoneNum());
            editTextAddress.setText(guser.getAddress());
            editTextIdType.setText(guser.getIdType());
            editTextIdNum.setText(guser.getIdNumber());
//            editTextPhone.setText(user.getPhoneNum());
        } else {
            guser = new User();
        }

        saveButton = (Button) view.findViewById(R.id.savebutton);
        saveButton.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        Log.i("insideOnClick ", " onButtonPressed");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        Editable nameEditable = null;
        Editable emailEditable = null;
        Editable phoneEditable = null;
        Editable addressEditable = null;
        Editable idTypeEditable = null;
        Editable idNumEditable = null;
        //TODO Harji: fill this value from update activity
//        User user = new User();

        int rowAffected = 0;
        switch (v.getId()) {
            case R.id.savebutton:

                nameEditable = editTextName.getText();
                emailEditable = editTextEmail.getText();
                phoneEditable = editTextPhone.getText();
                addressEditable = editTextAddress.getText();
                idTypeEditable = editTextIdType.getText();
                idNumEditable = editTextIdNum.getText();

                String name = String.valueOf(nameEditable);
                String email = String.valueOf(emailEditable);
                String phone = String.valueOf((phoneEditable == null) ? "" : phoneEditable);
                String address = String.valueOf((addressEditable == null) ? "" : addressEditable);
                String idType = String.valueOf((idTypeEditable == null) ? "" : idTypeEditable);
                String idNum = String.valueOf((idNumEditable == null) ? "" : idNumEditable);

                if (guser.getId() == null) {
                    if ((nameEditable != null) && (!nameEditable.toString().equals("")) && (emailEditable != null) && (!emailEditable.toString().equals(""))) {

                        Log.i("nameEditable", " name : " + name);
                        guser.setName(name);
                        guser.setEmail(email);
                        guser.setPhoneNum(phone);
                        guser.setAddress(address);
                        guser.setIdType(idType);
                        guser.setIdNumber(idNum);

                        long record = dbHandler.save(guser);
                        if (record > 0) {
                            SimpleAlertDialog.displayWithOK(getActivity(), " Record Saved");
                        }
                        UserListFragment fragment = new UserListFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                } else {
                    guser.setName(name);
                    guser.setEmail(email);
                    guser.setPhoneNum(phone);
                    guser.setAddress(address);
                    guser.setIdType(idType);
                    guser.setIdNumber(idNum);
                    rowAffected = dbHandler.update(guser);

                    if (rowAffected > 0) {
                        SimpleAlertDialog.displayWithOK(getActivity(), " Record Updated");
                    }

                    UserListFragment fragment = new UserListFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(this);
                    fragmentTransaction.replace(R.id.content_main, fragment);

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
