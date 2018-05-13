package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EMR_Breast_From extends AppCompatActivity {

   EditText patientName,emrType;
   String emr_data;boolean male=true;
   Button create;
//{"Clump Thickness":5,"Uniformity of Cell Size":1,"Uniformity of Cell Shape":1,"Marginal Adhesion":1,
// "Single Epithelial Cell Size":2,"Bare Nuclei":1,"Bland Chromatin":3,"Normal Nucleoli":1,"Mitoses":1}
    EditText clumpThickness,uniformityCellSize,uniformityCellShape,MarginalAdhesion,SingleEpithelialcellSize,
    BareNuclei,BlandChromatin,NormalNucleoli,Mitoses;
 FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_emr__brease__from);
        patientName=(EditText)findViewById(R.id.input_patient_name);
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





        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_emr();
                Log.d("data",emr_data);
                submitForm();
            }
        });
    }
    public void make_emr()
    {

        emr_data="Clump Thickness:"+clumpThickness.getText().toString()+"\nUniformity of Cell Shape:"+uniformityCellShape.getText().toString()+"\nUniformity of Cell Size:"+uniformityCellSize.getText().toString()+"\nMarginal Adhesion:"+MarginalAdhesion.getText().toString() +"\nSingle Epithelial Cell Size:"+SingleEpithelialcellSize.getText().toString()+"\nBare Nuclei:"+BareNuclei.getText().toString()+"\nBland Chromatin:"+BlandChromatin.getText().toString()+"\nNormal nucleoli:"+NormalNucleoli.getText().toString()+"\nMitoses:"+Mitoses.getText().toString();
    }
    private void submitForm() {

        Log.d("write","infunc");
        String pn = patientName.getText().toString();
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String type = "BreastCancer";


        EMR_Model emr = new EMR_Model(email,pn, type, emr_data);
        db.collection("EMR").document(pn+""+type).set(emr).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EMR_Breast_From.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("write","done");
                startActivity(new Intent(getApplicationContext(), EMRActivity.class));
               // finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EMR_Breast_From.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("write","fail");
            }
        });

    }





}
