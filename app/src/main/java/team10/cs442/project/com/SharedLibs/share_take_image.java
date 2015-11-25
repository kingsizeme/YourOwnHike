package team10.cs442.project.com.SharedLibs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import team10.cs442.project.com.MainActs.MainAct;
import team10.cs442.project.com.R;

public class share_take_image extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView myimg;
    private Bitmap saved_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_take_images);
        getActionBar().setTitle("Share Your Pic");

        myimg = (ImageView)findViewById(R.id.capturedImageview);
        Button mybut = (Button)findViewById(R.id.share_take_image);
        mybut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent mycam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(mycam, CAMERA_REQUEST);

            }
        });

        Button mybut2 = (Button)findViewById(R.id.share_pic_done);
        mybut2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(getContentResolver(), saved_img, "YOH" , "YOHP");
                Intent mycam = new Intent(getBaseContext(), Share_Input.class);
                startActivity(mycam);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            saved_img = (Bitmap)data.getExtras().get("data");
            myimg.setImageBitmap(saved_img);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gohome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainAct.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
