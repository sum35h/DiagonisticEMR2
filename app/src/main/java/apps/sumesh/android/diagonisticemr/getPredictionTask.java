package apps.sumesh.android.diagonisticemr;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getPredictionTask extends AsyncTask<String, Void, String> {


    private ProgressDialog pdia;

    private TextView textView;
    private Context context;

    public getPredictionTask(Context c,TextView textView) {
        this.textView = textView;
        context=c;
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
            final String BASE_URL = "https://disease-predict-app.herokuapp.com/predict?";
            final String QUERY_PARAM = "data";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,strings[0])

                    .build();
            URL url = new URL(builtUri.toString());
            Log.v("request", "Built URI " + builtUri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            Log.v("request",reader.toString());

            String inputString;
            while ((inputString = reader.readLine()) != null) {
                buffer.append(inputString);
                Log.v("request",buffer.toString());
            }

           // JSONObject topLevel = new JSONObject(buffer.toString());
          //  Log.v("request","buffer="+buffer.toString());
           // JSONObject main = topLevel.getJSONObject("Prediction");
            res = buffer.toString();

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    protected void onPostExecute(String res) {
        textView.setText(res);
        pdia.dismiss();
    }
}