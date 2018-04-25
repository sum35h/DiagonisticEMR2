package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EMR_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr__details);

        Intent i=getIntent();
        EMR_Model emr=(EMR_Model) i.getSerializableExtra("EMRObject");

        TextView pName,type,emr_data;
        pName=(TextView)findViewById(R.id.pname);
        type=(TextView)findViewById(R.id.type);
       emr_data=(TextView)findViewById(R.id.emr_data);

       pName.setText(emr.getPatient_name());
       type.setText(emr.getEMR_type());
       emr_data.setText(emr.getData());


    }
}
