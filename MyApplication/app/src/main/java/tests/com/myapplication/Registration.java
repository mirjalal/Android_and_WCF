package tests.com.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
    private String convertedImage = null;
    private byte[] inputData = null;
    /***************** local variables ******************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        /***************** find view elements ******************/
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
        /***************** find view elements ******************/


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
                        String profile_pic = convertedImage;

                        // construct URL for async task
                        String URL = "http://45.35.4.29/wcf/Service.svc/register/" + istiad + "/" + sifre + "/" + ad + "/" + soyad + "/" + bitirdiyi_mekteb + "/" + bitirdiyi_il + "/" + dogum_yeri + "/" + dogum_ili; //+ "/" + profile_pic;
                        // call WebService
                        new HttpAsyncTask().execute(URL);
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


//    @Override
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


//    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
//            throws FileNotFoundException {
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);
//
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
//
//        while (true) {
//            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
//                break;
//            width_tmp /= 2;
//            height_tmp /= 2;
//            scale *= 2;
//        }
//
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
//    }



//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    imageview.setImageURI(selectedImage);

/************************************ get real path ****************/
//                    birthday.setText(getRealPathFromURI(imageview.getContext(), selectedImage));
/************************************ get real path ****************/

//                    InputStream iStream = null;
//
//                    try {
//                        iStream = getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        birthday.setText(e.getLocalizedMessage());
//                    }
//
//                    try {
//                        inputData = getBytes(iStream);
//                    } catch (IOException e) {
//                        birthday.setText(e.getLocalizedMessage());
//                    }

//                    try {
//                        convertedImage = new String(inputData, "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        birthday.setText(e.getLocalizedMessage());
//                    }
//                    assert data != null;
//                    convertedImage = Base64.encodeToString(data, 0).replace('/', '-');
//                    birthday.setText(convertedImage);

                    Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                    convertedImage = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                 //   birthday.setText(Arrays.toString(getBytesFromBitmap(bitmap)));
                }
                break;
//            case 1:
////                if (resultCode == RESULT_OK) {
////                    Uri selectedImage = imageReturnedIntent.getData();
////                    imageview.setImageURI(selectedImage);
////                }
////                break;
//
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
////                    File getImageName = new File("" + selectedImage);
//
////                    Display display = getWindowManager().getDefaultDisplay();
////                    Point size = new Point();
////                    display.getSize(size);
////                    int width = size.x;
////                    int height = size.y;
//
//                    imageview.setImageURI(selectedImage);
//                    //    birthday.setText(getRealPathFromURI(imageview.getContext(), selectedImage));
//                    InputStream iStream = null;
//
//                    try {
//                        iStream = getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        birthday.setText(e.getLocalizedMessage());
//                    }
//
//                    byte[] inputData = null;
//                    try {
//                        inputData = getBytes(iStream);
//                    } catch (IOException e) {
//                        birthday.setText(e.getLocalizedMessage());
//                    }
//
//                    try {
//                        convertedImage = new String(inputData != null ? inputData : new byte[0], "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        birthday.setText("Couldn't convert from byte[] to string." + e.getMessage());
//                    }
//                }
//                break;
        }
    }


    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    /***************** get image from anywhere ******************/
    private void getImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // for Camera app
        Intent getIntent = new Intent(ACTION_GET_CONTENT); // for other image base apps (locations)
        getIntent.setType("image/*");
//        Intent pickIntent = new Intent(ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // for "Android System" option
//        pickIntent.setType("image/*");
        Intent chooserIntent = createChooser(getIntent, "Select Image");
//        chooserIntent.putExtra("crop", "true");
//        chooserIntent.putExtra("aspectX", 0);
//        chooserIntent.putExtra("aspectY", 0);
//        chooserIntent.putExtra("outputX", 200);
//        chooserIntent.putExtra("outputY", 150);
//        chooserIntent.putExtra("scale", true);
//        chooserIntent.putExtra("return-data", true);

        chooserIntent.putExtra(EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});
        startActivityForResult(chooserIntent, 0);
    }
    /***************** get image from anywhere ******************/


    /***************** get value from DatePicker & set to TextView ******************/
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

            birthday.setText(gun + "-" + ay + "-" + il);
        }
    };
    /***************** get value from DatePicker & set to TextView ******************/


    public static String GET(String URL) {
        String result = "";

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
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
