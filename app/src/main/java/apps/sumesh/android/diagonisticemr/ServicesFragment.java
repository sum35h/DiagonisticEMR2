package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sumesh on 14-04-2018.
 */


public class ServicesFragment extends Fragment {
    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        return fragment;
    }


//    private static RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private static RecyclerView recyclerView;
//    private static ArrayList<EventModel> data;
//    static View.OnClickListener myOnClickListener;
//    private static ArrayList<Integer> removedItems;
//    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final  View v= inflater.inflate(R.layout.services_fragment, container, false);

        Button disease_predict=(Button)v.findViewById(R.id.disease_pridict_but);


        disease_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(v.getContext(),Predict_disease_client.class);

                v.getContext().startActivity(i);
            }
        });
        Button dia_predict=(Button)v.findViewById(R.id.dia_pridict_but);


        dia_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(v.getContext(),Predict_dia_client.class);

                v.getContext().startActivity(i);
            }
        });
        return  v;
    }



}