package tests.com.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Login extends ActionBarActivity {

    String URL;
    Button login;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login:
                        String istiad = username.getText().toString();
                        String sifre = password.getText().toString();

                        String URL = "http://45.35.4.29/wcf/Service.svc/login/" + istiad + "/" + sifre;
//
                        //    new LoginAsync().execute(URL);
                        new MyAsyncTask().execute(URL);
                        break;
                }
            }
        };

        login.setOnClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_girish, menu);
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

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        InputStream inputStream = null;
        String result = "";
        private ProgressDialog progressDialog = new ProgressDialog(Login.this);
        String birthday = "";
        String born_place = "";
        String graduated_from = "";
        String graduated_in = "";
        String name = "";
        String surname = "";

//        @Override
//        protected void onPreExecute() {
//            progressDialog.setMessage("Downloading your data...");
//            progressDialog.show();
//            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                public void onCancel(DialogInterface arg0) {
//                    MyAsyncTask.this.cancel(true);
//                }
//            });
//        }

        @Override
        protected String doInBackground(String... params) {
//            ArrayList<NameValuePair> param = new ArrayList<>();

//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpGet httpget = new HttpGet(params[0]);
//                HttpResponse response = httpclient.execute(httpget);
//                ;
//                HttpEntity entity = response.getEntity();
//                inputStream = entity.getContent();
//
//
//            } catch (Exception e) {
//                result = e.getLocalizedMessage();
//            }


//            try {
//                HttpClient httpClient = new DefaultHttpClient();//ok
//                HttpPost httpPost = new HttpPost(params[0]);//ok
//                httpPost.setEntity(new UrlEncodedFormEntity(param));//ok
//                HttpResponse httpResponse = httpClient.execute(httpPost);//pk
//                HttpEntity httpEntity = httpResponse.getEntity();
//
//                // Read content & Log
//                inputStream = httpEntity.getContent();
//            } catch (UnsupportedEncodingException e1) {
//                Log.e("UnsupportedEncodingException", e1.toString());
//                e1.printStackTrace();
//            } catch (ClientProtocolException e2) {
//                Log.e("ClientProtocolException", e2.toString());
//                e2.printStackTrace();
//            } catch (IllegalStateException e3) {
//                Log.e("IllegalStateException", e3.toString());
//                e3.printStackTrace();
//            } catch (IOException e4) {
//                Log.e("IOException", e4.toString());
//                e4.printStackTrace();
//            }
//
//            // Convert response to string using String Builder
//            try {
//                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 6);
//                StringBuilder sBuilder = new StringBuilder();
//
//                String line = null;
//                while ((line = bReader.readLine()) != null) {
//                    sBuilder.append(line).append("\n");
//                }
//
//                inputStream.close();
//                result = sBuilder.toString();
//
//            } catch (Exception e) {
//              //  Log.e("Error: " + e.getLocalizedMessage());
//            }


            JSONObject json = null;
            String str = "";
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(params[0]);

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

            } catch (Exception e) {
                Log.e("ERRORRRRRRRR: ", e.getLocalizedMessage());
            }

            try {
                JSONArray jArray; //= new JSONArray(str);
                if(json != null)
                    Log.e("NULL", "dasdadadadadasdsd");
                else {
                    String notes = json.getString("loginResult");
                    jArray = new JSONArray(notes);
                    json = jArray.getJSONObject(0);

                    /********** get values from JSON object & set them  **********/
                    birthday = json.getString("birthday");
                    born_place = json.getString("born_place");
                    graduated_from = json.getString("graduated_from");
                    graduated_in = json.getString("graduated_in");
                    name = json.getString("name");
                    surname = json.getString("surname");
                    /********** set extra values to send them to Profile activity **********/
                }

            } catch (JSONException e) {
                Log.i("MESAJ VAR", e.getLocalizedMessage());
            }

//            try {
//                JSONArray jArray = new JSONArray(result);
//
//                String birthday = "";
//                String born_place = "";
//                String graduated_from = "";
//                String graduated_in = "";
//                String name = "";
//                String surname = "";
//
//                for (int i = 0; i < jArray.length(); i++) {
//
//                    JSONObject jObject = jArray.getJSONObject(i);
//
//                    /********** get values from JSON object & set them  **********/
//                    birthday = jObject.getString("birthday");
//                    born_place = jObject.getString("born_place");
//                    graduated_from = jObject.getString("graduated_from");
//                    graduated_in = jObject.getString("graduated_in");
//                    name = jObject.getString("name");
//                    surname = jObject.getString("surname");
//                    /********** set extra values to send them to Profile activity **********/
//                } // End Loop
//                /********** set extra values to send them to Profile activity **********/
//                intent.putExtra("json_name", name);
//                intent.putExtra("json_surname", surname);
//                intent.putExtra("json_graduated_from", graduated_from);
//                intent.putExtra("json_graduated_in", graduated_in);
//                intent.putExtra("json_born_place", born_place);
//                intent.putExtra("json_birthday", birthday);
//                /********** set extra values to send them to Profile activity **********/
//                startActivity(intent);
//            } catch (JSONException e) {
//               // Log.e("JSONException", "Error: " + e.toString());
//            } // catch (JSONException e)

            return result;
        }

        //   @Override
        protected void onPostExecute(String v) {
            //parse JSON data
            Intent intent = new Intent(getBaseContext(), Profile.class);
            /********** set extra values to send them to Profile activity **********/
            intent.putExtra("json_name", name);
            intent.putExtra("json_surname", surname);
            intent.putExtra("json_graduated_from", graduated_from);
            intent.putExtra("json_graduated_in", graduated_in);
            intent.putExtra("json_born_place", born_place);
            intent.putExtra("json_birthday", birthday);
            /********** set extra values to send them to Profile activity **********/
            startActivity(intent);
        }
    }

    public class LoginAsync extends AsyncTask<String, String, Void> {
        InputStream inputStream = null;
        String result = "";
        Intent intent = new Intent(getBaseContext(), Profile.class);

//        @Override
//        protected void onPreExecute() {
//
//        }

        @Override
        protected Void doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<>();

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(params[0]);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
            } catch (Exception e) {
                Log.e("Exception(e): ", e.getMessage());
                Log.d("Exception(d): ", e.getMessage());
                Log.w("Exception(w): ", e.getMessage());
                Log.i("Exception(i): ", e.getMessage());
            }

            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line).append("\n");
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                Log.e("StringBuilding: ", e.getLocalizedMessage());
            }

            return null;
        }

        protected void onPostExecute(String result) {
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    /********** get values from JSON object & set them  **********/
                    String birthday = jObject.getString("birthday");
                    String born_place = jObject.getString("born_place");
                    String graduated_from = jObject.getString("graduated_from");
                    String graduated_in = jObject.getString("graduated_in");
                    String name = jObject.getString("name");
                    String surname = jObject.getString("surname");
                    /********** set extra values to send them to Profile activity **********/

                    /********** set extra values to send them to Profile activity **********/
                    intent.putExtra("json_name", name);
                    intent.putExtra("json_surname", surname);
                    intent.putExtra("json_graduated_from", graduated_from);
                    intent.putExtra("json_graduated_in", graduated_in);
                    intent.putExtra("json_born_place", born_place);
                    intent.putExtra("json_birthday", birthday);
                    /********** set extra values to send them to Profile activity **********/
                    startActivity(intent);
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }
}
