package tests.com.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Main extends ActionBarActivity {

    Button giris, qeydiyyat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giris = (Button) findViewById(R.id.giris);
        qeydiyyat = (Button) findViewById(R.id.qeydiyyat);

        View.OnClickListener button_click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.giris:
                        startActivity(new Intent(Main.this, Login.class)); // Login activity-sini a??r
                        break;
                    case R.id.qeydiyyat:
                        startActivity(new Intent(Main.this, Registration.class)); // Registration activity-sini a??r
                        break;
                }
            }
        };

        giris.setOnClickListener(button_click);
        qeydiyyat.setOnClickListener(button_click);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_esas, menu);
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
