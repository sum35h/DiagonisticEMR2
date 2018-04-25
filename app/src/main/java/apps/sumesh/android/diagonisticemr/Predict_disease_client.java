package apps.sumesh.android.diagonisticemr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Predict_disease_client extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_predict_disease_client);
        Button submit=(Button)findViewById(R.id.but_submit);
       final EditText data=(EditText)findViewById(R.id.data);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) findViewById(R.id.tv);
                new getPredictionTask(Predict_disease_client.this,textView).execute(data.getText().toString());
            }
        });


    }









}
