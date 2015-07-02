package tests.com.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;


public class Registration extends ActionBarActivity {

    int gun, ay, il;
    Button submit, clear;
    EditText username, password, name, surname, graduated_from, graduated_in, born_place, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        submit         = (Button) findViewById(R.id.submit);
        clear          = (Button) findViewById(R.id.clear);
        username       = (EditText) findViewById(R.id.username);
        password       = (EditText) findViewById(R.id.password);
        name           = (EditText) findViewById(R.id.name);
        surname        = (EditText) findViewById(R.id.surname);
        graduated_from = (EditText) findViewById(R.id.graduated_from);
        graduated_in   = (EditText) findViewById(R.id.graduated_in);
        born_place     = (EditText) findViewById(R.id.born_place);
        birthday       = (EditText) findViewById(R.id.birthday);

        final Calendar calendar = Calendar.getInstance();
        gun = calendar.get(Calendar.DATE);
        ay  = calendar.get(Calendar.MONTH);
        il  = calendar.get(Calendar.YEAR);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        View.OnClickListener button_click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.submit:
                        String istiad = username.getText().toString();
                        String sifre = password.getText().toString();
                        String ad = name.getText().toString();
                        String soyad = surname.getText().toString();

                        String url = "http://45.35.4.29/wcf/Service.svc/register/" + istiad + "/" + sifre + "/" + ad + "/" + soyad;
                        new HttpAsyncTask().execute(url);
                        break;
                    case R.id.clear:
                        ViewGroup group = (ViewGroup) findViewById(R.id.form);
                        clearForm(group);
                        break;
                    case R.id.birthday:
                        showDialog(0);
                        break;
                }
            }
        };

        submit.setOnClickListener(button_click);
        clear.setOnClickListener(button_click);
        birthday.setOnClickListener(button_click);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 0)
            return new DatePickerDialog(this, dpickerlistener, il, ay, gun);

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            il = year;
            ay = monthOfYear;
            gun = dayOfMonth;

            birthday.setText(gun + "/" + ay + "/" + il);
        }
    };


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


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Done!", LENGTH_LONG).show();
        }
    }


    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qeydiyyat, menu);
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
