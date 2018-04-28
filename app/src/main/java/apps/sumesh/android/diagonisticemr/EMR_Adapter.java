package apps.sumesh.android.diagonisticemr;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EMR_Adapter extends RecyclerView.Adapter<EMR_Adapter.MyViewHolder> {

    public ArrayList<EMR_Model> dataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView pName;
            TextView emrType;

        private CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.pName = (TextView) itemView.findViewById(R.id.patientName);
            this.emrType = (TextView) itemView.findViewById(R.id.emr_type);

            cardView=(CardView)itemView.findViewById(R.id.card_view);

        }

    }

    public EMR_Adapter(ArrayList<EMR_Model> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emr_card_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        TextView en=holder.pName;
        TextView ed=holder.emrType;


        en.setText(dataSet.get(listPosition).getPatient_name());
        ed.setText(dataSet.get(listPosition).getEMR_type());

      // c.setText(dataSet.get(listPosition).getCount());
        holder.cardView.setTag(listPosition);
        Log.v("cardview",ed.getText().toString());
        if(ed.getText().toString().equals("Liver"))
            holder.cardView.setCardBackgroundColor(Color.	rgb(255, 255, 255));
        else
            holder.cardView.setCardBackgroundColor(Color.	rgb(122, 225, 217));
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
               Log.d("clicked","clicked :"+v.toString());
               int pos=(int)v.getTag();
               Toast.makeText(v.getContext(),"clicked item :"+dataSet.get(pos).getPatient_name(), Toast.LENGTH_SHORT).show();;
                Intent i = new Intent(v.getContext(),EMR_Details.class);
               i.putExtra("EMRObject",(EMR_Model)dataSet.get(pos));
               v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}