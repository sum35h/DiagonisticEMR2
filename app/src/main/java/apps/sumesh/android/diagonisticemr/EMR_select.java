package apps.sumesh.android.diagonisticemr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EMR_select extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr_select);
        Button diabets=findViewById(R.id.diabetes);
        Button liver=findViewById(R.id.liver);
        diabets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),EMR_Diabetes_From.class);
                startActivity(i);
            }
        });

        liver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),EMR_Liver_From.class);
                startActivity(i);
            }
        });
    }
}
