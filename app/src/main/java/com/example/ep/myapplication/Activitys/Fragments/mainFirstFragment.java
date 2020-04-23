package com.example.ep.myapplication.Activitys.Fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.ep.myapplication.Activitys.Activitys.MainActivity;
import com.example.ep.myapplication.Activitys.Adapters.MyAdapter;
import com.example.ep.myapplication.Activitys.Adapters.StateAdapter;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.Activitys.Services.DataService;
import com.example.ep.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mainFirstFragment.OnFirstFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mainFirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainFirstFragment extends Fragment {  // first fragment - listview with all states
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<State> allstates;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFirstFragmentInteractionListener mListener;

    public mainFirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainFirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static mainFirstFragment newInstance(String param1, String param2) {
        mainFirstFragment fragment = new mainFirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DataService ds = new DataService();
        final View v = inflater.inflate(R.layout.fragment_main_first, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // AsyncTask for getting states list

        new DataService.StateArrTask(this).execute();

        EditText inputSearch = (EditText) v.findViewById(R.id.inputSearch);

        // Adding items to listview

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                    mAdapter = new MyAdapter(getActivity(), ((MyAdapter)mAdapter).costumeFilter(allstates, cs.toString()));
                    recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFirstFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        SvgLoader.pluck().close();
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
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }

    public void populateRecycleView(ArrayList<State> states) {
        allstates = states;
        mAdapter = new MyAdapter(getActivity(), allstates);
        recyclerView.setAdapter(mAdapter);
    }
}
