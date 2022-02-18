package vn.edu.tnut.trungtien.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetSoLuong extends AppCompatActivity  {
    private Button btnHit;
    private TextView txtJson;
    private String sl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_so_luong);
        //new JsonTask().execute("https://tms.tnut.edu.vn/api.aspx?action=counter_online");
        btnHit = findViewById(R.id.btn_get);
        txtJson = findViewById(R.id.text_soluong);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonTask().execute("https://tms.tnut.edu.vn/api.aspx?action=counter_online");
                if(sl!= null) {
                    String kq = sl.substring(18, 22);
                    txtJson.setText(kq + " sinh viên");
                }
            }
        });

    }



    private class JsonTask extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                Log.d("test", "url= "+ url);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                    JSONObject myJson = new JSONObject(result.toString());
                    //Toast.makeText(GetSoLuong.this, "myJson get items = " + myJson.length(), Toast.LENGTH_LONG).show();
                    //JSONArray name = myJson.getJSONArray("data");
                    //JSONObject json1 = name.getJSONObject(0);
                    //String sl = json1.getString("counter_online");
                    Log.d("test", result.toString());
                    sl = result.toString();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
        }
    }
}