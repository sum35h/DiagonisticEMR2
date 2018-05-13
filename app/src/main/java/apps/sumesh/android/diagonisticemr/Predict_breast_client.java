package apps.sumesh.android.diagonisticemr;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Predict_breast_client extends AppCompatActivity {
    EditText clumpThickness,uniformityCellSize,uniformityCellShape,MarginalAdhesion,SingleEpithelialcellSize,
            BareNuclei,BlandChromatin,NormalNucleoli,Mitoses;
    EditText patientName,emrType;String gender="0";
    String emr_data;boolean male=true;
    Button create;
    private DocumentReference mDocRef;
    private FirebaseFirestore db;
    private TextInputLayout inputLayoutPregnancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr__brease__from);
        patientName=(EditText)findViewById(R.id.patient_name);
        // emrType=(EditText)findViewById(R.id.emr_type);

        clumpThickness=(EditText)findViewById(R.id.input_ClumpThickness);
        uniformityCellShape=(EditText)findViewById(R.id.input_uniformityCellShape);
        uniformityCellSize=(EditText)findViewById(R.id.input_uniformityCellSize);
        MarginalAdhesion=(EditText)findViewById(R.id.input_MarginalAdhesion);
        SingleEpithelialcellSize=(EditText)findViewById(R.id.input_singleEpithelialCellSize);
        BareNuclei=(EditText)findViewById(R.id.input_BareNuclei);
        BlandChromatin=(EditText)findViewById(R.id.input_blandChromatin);
        NormalNucleoli=(EditText)findViewById(R.id.input_normalNucleoli);
        Mitoses=(EditText)findViewById(R.id.input_mitoses);


        create=(Button)findViewById(R.id.btn_create_emr);
       create.setText("Predict");

        FirebaseApp.initializeApp(this);
        mDocRef= FirebaseFirestore.getInstance().collection("Events").document();
        db = FirebaseFirestore.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // make_emr();
               // Log.d("data",emr_data);
                new liverPredictionTask(clumpThickness.getText().toString(),gender,uniformityCellShape.getText().toString(),uniformityCellSize.getText().toString(),MarginalAdhesion.getText().toString(),SingleEpithelialcellSize.getText().toString(),BareNuclei.getText().toString(),BlandChromatin.getText().toString(),NormalNucleoli.getText().toString(),Mitoses.getText().toString(),Predict_breast_client.this  ).execute();
            }
        });
    }

    public void make_emr()
    {

      //  emr_data="Age:"+age.getText().toString()+"\nGender:"+gender+"\nTB:"+tb.getText().toString()+"\nDB:"+dbb.getText().toString() +"\nAlkphos:"+alkphos.getText().toString()+"\nSGPT:"+sgpt.getText().toString()+"\nSGOT:"+sgot.getText().toString()+"\nTP:"+tp.getText().toString()+"\nALB:"+alb.getText().toString()+"\nA/G ratio:"+ag.getText().toString();
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
