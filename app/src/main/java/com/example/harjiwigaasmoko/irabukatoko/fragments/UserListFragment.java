package com.example.harjiwigaasmoko.irabukatoko.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harjiwigaasmoko.irabukatoko.R;
import com.example.harjiwigaasmoko.irabukatoko.customs.UserAdapter;
import com.example.harjiwigaasmoko.irabukatoko.dummy.DummyContent;
import com.example.harjiwigaasmoko.irabukatoko.entity.User;
import com.example.harjiwigaasmoko.irabukatoko.handler.DatabaseHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class UserListFragment extends android.support.v4.app.Fragment implements AbsListView.OnItemClickListener,AdapterView.OnItemLongClickListener,View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<User>usersList = new ArrayList<User>();
    private Activity activity;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private UserAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static UserListFragment newInstance(String param1, String param2) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = getActivity();
//        activity.setTitle("Users");
        DatabaseHandler dbHandler = DatabaseHandler.getInstance(activity);
        usersList = dbHandler.findAll();

        for(Iterator<User> i =usersList.listIterator();i.hasNext();){
            User u = i.next();
            Log.i("ListView","user name: "+u.getName());
            Log.i("ListView","user email: "+u.getEmail());
        }
        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<User>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, usersList);
//        mAdapter = new ArrayAdapter<User>(getActivity(),R.layout.row,usersList);
        mAdapter = new UserAdapter(getActivity(),R.layout.row,usersList);

//        checkButtonClick();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userlist, container, false);


        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setLongClickable(true);
        mListView.setOnItemLongClickListener(this);

        Button myButton = (Button) view.findViewById(R.id.button_next);
        myButton.setOnClickListener(this);
        return view;
    }

//    @Override
//    public void onAttach(Context activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            mListener.onFragmentInteraction(String.valueOf(usersList.get(position).getId()));
            User user = (User) parent.getItemAtPosition(position);
            Toast.makeText(getContext(),
                    "Clicked on Row: " + user.getName(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onClick(View v) {
        StringBuffer responseText = new StringBuffer();
        responseText.append("The following were selected...\n");

        ArrayList<User> countryList = (ArrayList<User>) mAdapter.getUsers();
        for(int i=0;i<countryList.size();i++){
            User country = countryList.get(i);
            if(country.isSelected()){
                responseText.append("\n" + country.getName());
            }
        }

        Toast.makeText(activity.getApplicationContext(),
                responseText, Toast.LENGTH_LONG).show();
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
        public void onFragmentInteraction(String id);
    }

    private void checkButtonClick() {


        Button myButton = (Button) activity.findViewById(R.id.button_next);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<User> countryList = (ArrayList<User>) mAdapter.getUsers();
                for(int i=0;i<countryList.size();i++){
                    User country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(activity.getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }


}
