package apps.sumesh.android.diagonisticemr;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Predict_liver_client extends AppCompatActivity {
    EditText age,tb,dbb,alkphos,sgpt,sgot,tp,alb,ag;
    EditText patientName,emrType;String gender="0";
    String emr_data;boolean male=true;
    Button create;
    private DocumentReference mDocRef;
    private FirebaseFirestore db;
    private TextInputLayout inputLayoutPregnancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr__liver__from);
        patientName=(EditText)findViewById(R.id.patient_name);
        // emrType=(EditText)findViewById(R.id.emr_type);

        age=(EditText)findViewById(R.id.input_age);
        tb=(EditText)findViewById(R.id.input_tb);
        dbb=(EditText)findViewById(R.id.db);
        alkphos=(EditText)findViewById(R.id.alkphos);
        sgpt=(EditText)findViewById(R.id.sgpt);
        sgot=(EditText)findViewById(R.id.sgot);
        tp=(EditText)findViewById(R.id.tp);
        alb=(EditText)findViewById(R.id.alb);
        ag=(EditText)findViewById(R.id.ag);

        create=(Button)findViewById(R.id.btn_create_emr);
       create.setText("Predict");
//	{"Age":46,"Gender":1,"TB":1.8,"DB":0.7,"Alkphos":208,"Sgpt":19,"Sgot":14,"TP":7.6,"ALB":4.4,"A/G Ratio":1.3}
        FirebaseApp.initializeApp(this);
        mDocRef= FirebaseFirestore.getInstance().collection("Events").document();
        db = FirebaseFirestore.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_emr();
                Log.d("data",emr_data);
                new liverPredictionTask(age.getText().toString(),gender,tb.getText().toString(),dbb.getText().toString(),alkphos.getText().toString(),sgpt.getText().toString(),sgot.getText().toString(),tp.getText().toString(),alb.getText().toString(),ag.getText().toString(),Predict_liver_client.this  ).execute();
            }
        });
    }

    public void make_emr()
    {

        if(male)
            gender="1";
        //   EditText age,gender,tb,db,alkphos,sgpt,sgot,sgot,tp,alb,ag;
        emr_data="Age:"+age.getText().toString()+"\nGender:"+gender+"\nTB:"+tb.getText().toString()+"\nDB:"+dbb.getText().toString() +"\nAlkphos:"+alkphos.getText().toString()+"\nSGPT:"+sgpt.getText().toString()+"\nSGOT:"+sgot.getText().toString()+"\nTP:"+tp.getText().toString()+"\nALB:"+alb.getText().toString()+"\nA/G ratio:"+ag.getText().toString();
    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.gender_male:


                male = true;
                break;
            case R.id.gender_female:
                if (checked)

                    male = false;
                break;
        }
    }
}
