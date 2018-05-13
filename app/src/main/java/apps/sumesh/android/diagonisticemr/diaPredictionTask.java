package apps.sumesh.android.diagonisticemr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class diaPredictionTask extends AsyncTask<String, Void, String> {
    private String pregnancies,glucose,insulin,bmi,dpf,thickness,age,bp;

    private ProgressDialog pdia;

    private TextView textView;
    private Context context;


    public diaPredictionTask(String pregnancies, String glucose, String bp,String insulin, String bmi, String dpf, String thickness, String age, Context context) {
        this.pregnancies = pregnancies;
        this.glucose = glucose;
        this.insulin = insulin;
        this.bmi = bmi;
        this.dpf = dpf;
        this.thickness = thickness;
        this.age = age;
        this.context = context;
        this.bp=bp;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pdia = new ProgressDialog(context);
       pdia.setMessage("Loading...");
       pdia.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String res="";
        try {
            final String BASE_URL = "https://classification-app.herokuapp.com/predict_dia";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()


                    .build();
            URL url = new URL(builtUri.toString());
            Log.v("request", "Built URI " + builtUri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();



//	{"Pregnancies":6,"Glucose":148,"BloodPressure":72,"SkinThickness":35,"Insulin":0,"BMI":33.6,"DiabetesPedigreeFunction":0.627,"Age":50}
            Log.v("request","here:");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("Pregnancies", pregnancies);
            jsonParam.put("Glucose", glucose);
            jsonParam.put("BloodPressure", bp);
            jsonParam.put("SkinThickness",thickness);

            jsonParam.put("Insulin", insulin);
            jsonParam.put("BMI", bmi);
            jsonParam.put("DiabetesPedigreeFunction", dpf);
            jsonParam.put("Age", age);

            JSONArray jsonArray=new JSONArray();
            jsonArray.put(jsonParam);
            Log.v("request",jsonParam.toString());
            Log.v("request",jsonArray.toString());


            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonArray.toString());
            writer.close();
            outputStream.close();

//            printout = new DataOutputStream(urlConnection.getOutputStream ());
//            printout.writeBytes(URLEncoder.encode(jsonArray.toString(),"UTF-8"));
//            printout.flush ();
          //  Log.v("request",printout.toString());
// Read the input stream into a String
            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            res = sb.toString();
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
    @Override
    protected void onPostExecute(String res) {
       // textView.setText(res);
        Log.v("request",res);
        pdia.dismiss();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        String result = res.substring(res.indexOf("[") + 1, res.indexOf("]"));
        result=result.trim();
        Log.v("result:","["+result+"]");
        if(result.equals("1"))
            result="Positive: You are at risk ";
        else
            result="Negative:You are not at risk";
        alertDialogBuilder.setMessage(result);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}