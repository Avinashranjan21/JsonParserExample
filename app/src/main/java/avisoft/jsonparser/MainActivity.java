package avisoft.jsonparser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {
    JSONArray contacts = null;
    String[] values = new String[] { "Monday weather",
            "Tuesday weather",
            "Wednesday weather",
            "Thursday weather",
            "Friday weather",
            "Saturday weather",
            "Sunday weather",
            "Monday weather"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adpater = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,values);
        mListView.setAdapter(adpater);

    }

    public class Data extends AsyncTask<String,Void,String> {

        String data;
        @Override
        protected String doInBackground(String... params) {

            try {
                data= downloadUrl(params[0]).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {

        }

//Method to parse Json Data from inputStream
        public void parseJson(String stream){
            String jsonStr = stream;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    contacts = jsonObj.getJSONArray("contacts");

                }catch (Exception e){
                    Log.e("Error", String.valueOf(e));
                }

            }

        }



// Method to get Weather data from openweather api
        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();
            return stream;
        }

    }
}
