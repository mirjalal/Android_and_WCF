package tests.com.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


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
                        new LoginAsync().execute(URL);
                        break;
                }
            }
        };

        login.setOnClickListener(clickListener);
        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    login.performClick();
                    return true;
                }
                return false;
            }
        });
    }


    public class LoginAsync extends AsyncTask<String, Void, String> {
        String result = "";
        String json_name = "";
        String json_surname = "";
        String json_birthday = "";
        String json_born_place = "";
        String json_graduated_in = "";
        String json_graduated_from = "";
        InputStream inputStream = null;
        ProgressDialog progressDialog = new ProgressDialog(Login.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data.\nPlease, wait...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    LoginAsync.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(params[0]);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
            } catch (Exception e) {
                result = e.getLocalizedMessage();
            }

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
                Log.w("Error: ", e.getLocalizedMessage());
            }

            try {
                JSONObject jsonRootObject = new JSONObject(result);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("loginResult");

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    json_name = jsonObject.optString("name");
                    json_surname = jsonObject.optString("surname");
                    json_graduated_from = jsonObject.optString("graduated_from");
                    json_graduated_in = jsonObject.optString("graduated_in");
                    json_born_place = jsonObject.optString("born_place");
                    json_birthday = jsonObject.optString("birthday");
                }
            } catch (JSONException e) {
                Log.w("JSON Parse error: ", e.toString());
            }

            return null;
        }

        //   @Override
        protected void onPostExecute(String v) {
            //parse JSON data
            Intent intent = new Intent(getBaseContext(), Profile.class);
            /********** set extra values to send them to Profile activity **********/
            intent.putExtra("name", json_name);
            intent.putExtra("surname", json_surname);
            intent.putExtra("graduated_from", json_graduated_from);
            intent.putExtra("graduated_in", json_graduated_in);
            intent.putExtra("born_place", json_born_place);
            intent.putExtra("birthday", json_birthday);
            /********** set extra values to send them to Profile activity **********/
            startActivity(intent);
        }
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
}