package tests.com.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    Button btn_tesdiq;
    EditText txt_username;
    EditText txt_password;
    public TextView netice;
    public TextView textview3;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_tesdiq = (Button) findViewById(R.id.btn_tesdiq);
        txt_username = (EditText) findViewById(R.id.e_istifadeciAdi);
        txt_password = (EditText) findViewById(R.id.e_sifre);
        netice = (TextView) findViewById(R.id.netice);
        textview3 = (TextView) findViewById(R.id.textView3);

        View.OnClickListener button_click_action = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_tesdiq:
                        String istiad = txt_username.getText().toString();
                        String sifre = txt_password.getText().toString();
                        String url = "http://45.35.4.29/wcf/Service.svc/login?istiad=" + istiad + "&sifre=" + sifre;
//                        http://45.35.4.29/wcf/Service.svc/login?istiad=celal&sifre=1234
                        new HttpAsyncTask().execute(url);
                        break;
                }
            }
        };

        btn_tesdiq.setOnClickListener(button_click_action);
    }


    public static String GET(String url) {
        String result = "";

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response;
            try {
                response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            result = e.getLocalizedMessage();
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        return result;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            netice.setText(result);
        }
    }


//    public class sync_login extends AsyncTask<String, Void, String>
//    {
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet("http://45.35.4.29/wcf/Service.svc/login?istiad=celal&sifre=1234");
//                HttpResponse response = client.execute(request);
//
//                InputStream in = response.getEntity().getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//                StringBuilder str = new StringBuilder();
//                String line = null;
//
//                while ((line = reader.readLine()) != null) {
//                    str.append(line);
//                }
//
//                in.close();
//
//                if (str.toString().equals("<?xml version=\"1.0\" encoding=\"utf-8\"?><string>1</string>"))
//                    netice.setText("Login Successful");
//                else if (str.toString().equals("<?xml version=\"1.0\" encoding=\"utf-8\"?><string>0</string>"))
//                    netice.setText("Login Failed");
//                else
//                    netice.setText("nese problem");
//            } catch (Exception ex) {
//                netice.setText(ex.toString());
//            }
//
//            return "Tamamlandi";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            exception.setText("Tamamlandi"); // txt.setText(result);
//            // might want to change "executed" for the returned string passed
//            // into onPostExecute() but that is upto you
//        }
//
//        @Override
//        protected void onPreExecute() {}
//
////        @Override
////        protected void onProgressUpdate(Integer... values)
////        {
////            ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
////            progress.setProgress(values[0]);
////        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

