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
	//{"Age":46,"Gender":1,"TB":1.8,"DB":0.7,"Alkphos":208,"Sgpt":19,"Sgot":14,"TP":7.6,"ALB":4.4,"A/G Ratio":1.3}
public class liverPredictionTask extends AsyncTask<String, Void, String> {
    private String age,gender,tb,db,alk,sgpt,sgot,tp,alb,ag;

    private ProgressDialog pdia;

    private TextView textView;
    private Context context;

        public liverPredictionTask(String age, String gender, String tb, String db, String alk, String sgpt, String sgot, String tp, String alb, String ag, Context context) {
            this.age = age;
            this.gender = gender;
            this.tb = tb;
            this.db = db;
            this.alk = alk;
            this.sgpt = sgpt;
            this.sgot = sgot;
            this.tp = tp;
            this.alb = alb;
            this.ag = ag;
            this.context = context;
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
            final String BASE_URL = "https://classification-app.herokuapp.com/predict_liver";

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


            //{"Age":46,"Gender":1,"TB":1.8,"DB":0.7,"Alkphos":208,"Sgpt":19,"Sgot":14,"TP":7.6,"ALB":4.4,"A/G Ratio":1.3}on":0.627,"Age":50}
            Log.v("request","here:");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("Age", age);
            jsonParam.put("Gender", gender);
            jsonParam.put("TB", tb);
            jsonParam.put("DB",db);

            jsonParam.put("Alkphos", alk);
            jsonParam.put("Sgpt", sgpt);
            jsonParam.put("Sgot", sgot);
            jsonParam.put("TP", tp);
            jsonParam.put("ALB", alb);
            jsonParam.put("A/G Ratio",ag);

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