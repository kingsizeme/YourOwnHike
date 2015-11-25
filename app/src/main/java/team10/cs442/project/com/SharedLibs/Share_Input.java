package team10.cs442.project.com.SharedLibs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team10.cs442.project.com.MainActs.MainAct;
import team10.cs442.project.com.R;

public class Share_Input extends Activity {

    public String username = "";
    private static final int RESULT_LOAD_IMG = 1888;
    private ImageView myimg;
    private Bitmap saved_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share__input);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            username = extras.getString("user_name");
        }


        Button mypic = (Button)findViewById(R.id.ch_mypic);
        mypic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                saved_img = (Bitmap)BitmapFactory.decodeFile(imgDecodableString);
                ImageView imgView = (ImageView) findViewById(R.id.selectedimage);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(saved_img);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void insert_moment(View view){
        if(username.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Sorry, Log in required",Toast.LENGTH_LONG).show();
            back_to_main();
        }
        EditText editTexta = (EditText)findViewById(R.id.share_input_text);
        EditText editTextb = (EditText)findViewById(R.id.share_input_location);
        String moments = editTexta.getText().toString();
        String location = editTextb.getText().toString();
        parse_insert_moments(moments,location, saved_img);
        back_to_main();
    }

    public void back_to_main(){
        Intent intent = new Intent(this, MainAct.class);
        intent.putExtra("from_act","Share_input");
        startActivity(intent);
    }

    public void parse_insert_moments(String data, String location, Bitmap my_image){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat myform = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String timestamp = myform.format(date);
        ParseFile pFile = null ;


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        saved_img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        pFile = new ParseFile("saved.jpg", stream.toByteArray());

        ParseObject shared_moments = new ParseObject("SharedMoments");
        shared_moments.put("Moment", data);
        shared_moments.put("UserName", MainAct.user_saved);
        shared_moments.put("Date",timestamp);
        shared_moments.put("Location", location);
        shared_moments.put("Image",pFile);
        shared_moments.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.v("done","User update saved!");
                } else {
                    // The save failed.
                    Log.v("error","User update error: " + e.toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share__input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.takeimg) {
            Intent intent = new Intent(getBaseContext(), share_take_image.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainAct.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
