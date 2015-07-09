package tests.com.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class Profile extends ActionBarActivity {

    int gun, ay, il;
    Button update, clear;
    TextView profile_pic, birthday;
    EditText name, surname, graduated_from, graduated_in, born_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String name_value = "";
        String surname_value = "";
        String graduated_from_value = "";
        String graduated_in_value = "";
        String born_place_value = "";
        String birthday_value = "";

        /***************** get values from view elements ******************/
        update = (Button) findViewById(R.id.update);
        clear = (Button) findViewById(R.id.clear);
        name = (EditText) findViewById(R.id.profile_name);
        surname = (EditText) findViewById(R.id.profile_surname);
        graduated_from = (EditText) findViewById(R.id.profile_graduated_from);
        graduated_in = (EditText) findViewById(R.id.profile_graduated_in);
        born_place = (EditText) findViewById(R.id.profile_born_place);
        birthday = (TextView) findViewById(R.id.profile_birthday);
        /***************** get values from view elements ******************/


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name_value = extras.getString("json_name");
            surname_value = extras.getString("json_surname");
            graduated_from_value = extras.getString("json_graduated_from");
            graduated_in_value = extras.getString("json_graduated_in");
            born_place_value = extras.getString("json_born_place");
            birthday_value = extras.getString("json_birthday");

            /***************** set values to view elements ******************/
            name.setText(name_value);
            surname.setText(surname_value);
            graduated_from.setText(graduated_from_value);
            graduated_in.setText(graduated_in_value);
            born_place.setText(born_place_value);
            birthday.setText(birthday_value);
            /***************** set values to view elements ******************/
        }

        /***************** set calendar to current date ******************/
        final Calendar calendar = Calendar.getInstance();
        gun = calendar.get(Calendar.DATE);
        ay = calendar.get(Calendar.MONTH);
        il = calendar.get(Calendar.YEAR);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        /***************** set calendar to current date ******************/
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
