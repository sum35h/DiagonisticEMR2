package apps.sumesh.android.diagonisticemr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Predict_dia_client extends AppCompatActivity {
    EditText pregnancies,glucose,insulin,bmi,dpf,thickness,age,bp;
    EditText patientName,emrType;
    String emr_data;boolean male=true;
    Button create;
    private DocumentReference mDocRef;
    private FirebaseFirestore db;
    private TextInputLayout inputLayoutPregnancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr__diabetes__from);
        patientName=(EditText)findViewById(R.id.patient_name);
        // emrType=(EditText)findViewById(R.id.emr_type);
        pregnancies=(EditText)findViewById(R.id.input_pregnancies);
        glucose=(EditText)findViewById(R.id.glucose);
        insulin=(EditText)findViewById(R.id.insulin);
        bmi=(EditText)findViewById(R.id.BMI);
        dpf=(EditText)findViewById(R.id.DPF);
        thickness=(EditText)findViewById(R.id.thickness);
        age=(EditText)findViewById(R.id.age);
        create=(Button)findViewById(R.id.btn_create_emr);
       create.setText("Predict");
       bp=(EditText)findViewById(R.id.bp);
        FirebaseApp.initializeApp(this);
        mDocRef= FirebaseFirestore.getInstance().collection("Events").document();
        db = FirebaseFirestore.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_emr();
                Log.d("data",emr_data);
                new diaPredictionTask(pregnancies.getText().toString(),glucose.getText().toString(),bp.getText().toString(),insulin.getText().toString(),
                        bmi.getText().toString(),dpf.getText().toString(),thickness.getText().toString(),age.getText().toString()
                      ,Predict_dia_client.this  ).execute();
            }
        });
    }
    public void make_emr()
    {
        if(male)
            pregnancies.setText("0");
        emr_data="Age:"+age.getText().toString()+"\nPregnancies:"+pregnancies.getText().toString()+"\ninsulin:"+insulin.getText().toString()+"\nalucose:"+glucose.getText().toString() +"\nBMI:"+bmi.getText().toString()+"\nDPF:"+dpf.getText().toString()+"\nThickness:"+thickness.getText().toString();
    }
    private void submitForm() {

        Log.d("write","infunc");
        String pn = patientName.getText().toString();
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String type = "Diabetese";


        EMR_Model emr = new EMR_Model(email,pn, type, emr_data);
        db.collection("EMR").document().set(emr).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Predict_dia_client.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("write","done");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Predict_dia_client.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("write","fail");
            }
        });
        finish();
    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.gender_male:
                if (checked)
                    pregnancies.setVisibility(View.GONE);
                male=true;
                break;
            case R.id.gender_female:
                if (checked)
                    pregnancies.setVisibility(View.VISIBLE);
                male=false;
                break;
        }
    }
}
