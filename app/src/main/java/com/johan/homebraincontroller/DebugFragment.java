package com.johan.homebraincontroller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;


public class DebugFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static DebugFragment newInstance() {
        return new DebugFragment();
    }

    public DebugFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debug, container, false);
        final Button button = (Button) view.findViewById(R.id.sendbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String type = ((TextView) getView().findViewById(R.id.msgtypetext)).getText().toString();
                String id = ((TextView) getView().findViewById(R.id.msgidtext)).getText().toString();
                String data = ((TextView) getView().findViewById(R.id.msgdatatext)).getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("type", type);
                params.put("data", data);
                RestController.getInstance().createJsonPostRequest(new JSONObject(params));
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
