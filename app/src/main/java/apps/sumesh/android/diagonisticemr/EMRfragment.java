package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Sumesh on 14-04-2018.
 */


public class EMRfragment extends Fragment {
    public static EMRfragment newInstance() {
        EMRfragment fragment = new EMRfragment();
        return fragment;
    }


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<EMR_Model> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.emr_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        fab=(FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),EMR_select.class);
                startActivity(i);
            }
        });
//
     //layoutManager = new LinearLayoutManager(getContext());
        layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//
        data = new ArrayList<EMR_Model>();
//
        data.add(new EMR_Model(
                "Saurabh",
                "Cancer" ,
                "muasdadsic"

        ));
        data.add(new EMR_Model(
                "Shantam",
                "AIDS",
                "dancasdasde"

        ));
        final ArrayList<EMR_Model> eventsList = new ArrayList<EMR_Model>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("EMR").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException e) {
                if(e==null)
                {
                    eventsList.clear();
                    for(DocumentSnapshot doc :value){

                        EMR_Model event = doc.toObject(EMR_Model.class);
                        eventsList.add(event);
                        Log.e("emr", "emr read" );
                    }
                    adapter = new EMR_Adapter(eventsList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
        });

//
         adapter = new EMR_Adapter(eventsList);
         recyclerView.setAdapter(adapter);
          //  recyclerView.setAdapter(new EMR_Adapter(data) );
        return  v;
    }



}