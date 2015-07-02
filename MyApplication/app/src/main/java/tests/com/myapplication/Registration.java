package tests.com.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.content.Intent.*;
import static android.widget.Toast.LENGTH_LONG;


public class Registration extends ActionBarActivity {

    /***************** local variables ******************/
    int gun, ay, il;
    Button submit, clear;
    TextView profile_pic, birthday;
    EditText username, password, name, surname, graduated_from, graduated_in, born_place;
    ImageView imageview;

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    /***************** local variables ******************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        /***************** get values from view elements ******************/
        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        graduated_from = (EditText) findViewById(R.id.graduated_from);
        graduated_in = (EditText) findViewById(R.id.graduated_in);
        born_place = (EditText) findViewById(R.id.born_place);
        birthday = (TextView) findViewById(R.id.birthday);
        profile_pic = (TextView) findViewById(R.id.profile_pic);
        imageview = (ImageView) findViewById(R.id.imageView);
        /***************** get values from view elements ******************/


        /***************** set calendar to current date ******************/
        final Calendar calendar = Calendar.getInstance();
        gun = calendar.get(Calendar.DATE);
        ay = calendar.get(Calendar.MONTH);
        il = calendar.get(Calendar.YEAR);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        /***************** set calendar to current date ******************/


        View.OnClickListener button_click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.submit: // "Submit" button
                        String istiad = username.getText().toString();
                        String sifre = password.getText().toString();
                        String ad = name.getText().toString();
                        String soyad = surname.getText().toString();
                        String bitirdiyi_mekteb = graduated_from.getText().toString();
                        String bitirdiyi_il = graduated_in.getText().toString();
                        String dogum_yeri = born_place.getText().toString();
                        String dogum_ili = birthday.getText().toString();


                        // call WebService
                        String url = "http://45.35.4.29/wcf/Service.svc/register/" + istiad + "/" + sifre + "/" + ad + "/" + soyad;
                        new HttpAsyncTask().execute(url);
                        break;
                    case R.id.clear:  // "Clear" button
                        ViewGroup group = (ViewGroup) findViewById(R.id.form);
                        clearForm(group);
                        break;
                    case R.id.birthday: // "birthday" TextView
                        showDialog(0);
                        break;
                    case R.id.profile_pic: // "profile_pic" TextView
                        getImage();
                        break;
                }
            }
        };

        submit.setOnClickListener(button_click);
        clear.setOnClickListener(button_click);
        birthday.setOnClickListener(button_click);
        profile_pic.setOnClickListener(button_click);
    }


    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch (requestCode) {
//            case 3:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    Cursor cursor = getContentResolver().query(
//                            selectedImage, filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String filePath = cursor.getString(columnIndex);
//                    cursor.close();
//
//
//                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//                    File getImageName = new File("" + selectedImage);
//
//                    profile_pic.setText("Choice image: " + getImageName.getName());
//                }
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(selectedImage.getPath());

                    File getImageName = new File("" + selectedImage);
                    profile_pic.setText(selectedImage.getPath() + getImageName.getName());
                    imageview.setImageURI(selectedImage);

/******************************* last changes *********************************/
//                    File pickedImgagePath = new File(ImageName);
//                    BufferedImage bufferedImage = ImageIO.read(selectedImage.getPath());

                    // get DataBufferBytes from Raster
//                    WritableRaster raster = bufferedImage .getRaster();
//                    DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
/******************************* last changes *********************************/

                    //yourSelectedImage.recycle();
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
        }
    }

    /***************** get value from DatePicker & set to EditText ******************/
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 0)
            return new DatePickerDialog(this, datepickerlistener, il, ay, gun);

        return null;
    }


    private DatePickerDialog.OnDateSetListener datepickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            il = year;
            ay = monthOfYear + 1;
            gun = dayOfMonth;

            birthday.setText(gun + "/" + ay + "/" + il);
        }
    };
    /***************** get value from DatePicker & set to EditText ******************/



    /***************** get image from anywhere ******************/
    private void getImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // for Camera app
        Intent getIntent = new Intent(ACTION_GET_CONTENT); // for other image base apps (locations)
        getIntent.setType("image/*");
//        Intent pickIntent = new Intent(ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // for "Android System" option
//        pickIntent.setType("image/*");
        Intent chooserIntent = createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});
        startActivityForResult(chooserIntent, 0);
    }
    /***************** get image from anywhere ******************/


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


    /***************** clear form ******************/
    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText)
                ((EditText) view).setText("");

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }
    /***************** clear form ******************/


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
