package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EMR_Details extends AppCompatActivity {
    FirebaseFirestore db; TextView pName,type,emr_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr__details);

        Intent i=getIntent();
        EMR_Model emr=(EMR_Model) i.getSerializableExtra("EMRObject");


        pName=(TextView)findViewById(R.id.pname);
        type=(TextView)findViewById(R.id.type);
       emr_data=(TextView)findViewById(R.id.emr_data);

       pName.setText(emr.getPatient_name());
       type.setText(emr.getEMR_type());
       emr_data.setText(emr.getData());

        db=FirebaseFirestore.getInstance();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.emr_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {


            case R.id.menu_delete:
                Toast.makeText(EMR_Details.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
                db.collection("EMR").document(pName.getText().toString()+""+type.getText().toString())

                        .delete().addOnSuccessListener(new OnSuccessListener< Void >() {

                    @Override

                    public void onSuccess(Void aVoid) {

                        Toast.makeText(EMR_Details.this, "Data deleted !",

                                Toast.LENGTH_SHORT).show();

                    }

                });
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
