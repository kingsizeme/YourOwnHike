package team10.cs442.project.com.MainActs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;

import java.text.SimpleDateFormat;

import team10.cs442.project.com.GPS_tracking.Tracking_map;
import team10.cs442.project.com.Locations;
import team10.cs442.project.com.Logon_Or_Reg;
import team10.cs442.project.com.MyLog;
import team10.cs442.project.com.R;
import team10.cs442.project.com.SharedLibs.MomentItemListActivity;
import team10.cs442.project.com.SharedLibs.Share_Input;

public class MainAct extends Activity {

    public static String init_check = "unfinished";
    String log_check = "unfinished";
    public static String user_saved = "";
    String pass_saved = "";
    String email_saved = "";

    public static final String PREF_N = "PrefFile";
    public static String NETSTATE = "offline";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setTitle("YourOwnHike");

        SharedPreferences myp = getSharedPreferences(PREF_N, MODE_PRIVATE);

        if(myp.contains("user_name")){
            user_saved = myp.getString("user_name","");
            TextView text = (TextView) findViewById(R.id.username_show);
            text.setText(user_saved);
            Button log_button = (Button)findViewById(R.id.but_log_in);
            log_button.setText("Log Off");
            log_check = "finished";
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Log.v("intent send", "returnedA");
            init_check = extras.getString("initialized");
            Log.v("intent send", extras.getString("from_act"));
            String tmp = extras.getString("from_act");
            if(tmp.equals("Logon_Or_Reg") ){
                //if returned from log in, then set user information
                Log.v("intent send", "returned");
                set_user_info(extras);
            }
            Log.v("intent send", "passed");
        }

        if(init_check == "unfinished"){
            Parse.initialize(this, "R8PJocLyYvzVXgkR5YkT8JDoFZx5ns6AnRGaZE5u", "x4xsYr6wBjSE6haZwXwKiL8lC8vmZfe5mtfg7VVD");
            ParseInstallation.getCurrentInstallation().saveInBackground();
            init_check = "finished";
        }

        if(isNetworkAvailable()){
            NETSTATE = "online";
        }
        Toast.makeText(getApplicationContext(), "Internet " + NETSTATE, Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void set_user_info(Bundle extras){
        user_saved = extras.getString("username","");
        Log.v("intent send", user_saved);
        log_check = "finished";
        TextView text = (TextView) findViewById(R.id.username_show);
        Button log_button = (Button)findViewById(R.id.but_log_in);
        SharedPreferences myp = getSharedPreferences(PREF_N, MODE_PRIVATE);
        SharedPreferences.Editor editor = myp.edit();

        text.setText(user_saved);
        log_button.setText("Log Off");
        editor.putString("user_name", user_saved);
        editor.putString("user_password", extras.getString("password"));
        editor.putString("user_email", extras.getString("email"));
        editor.commit();

    }

    public void resetpref ()
    {
        SharedPreferences myp = getSharedPreferences(PREF_N, MODE_PRIVATE);
        SharedPreferences.Editor editor = myp.edit();
        editor.clear();
        editor.commit();

    }

    public void OpenLocations(View view){
        Intent intent = new Intent(this,Locations.class);
        intent.putExtra("user_name",user_saved);
        startActivity(intent);
    }

    public void OpenLogs(View view){
        Intent intent = new Intent(this,MyLog.class);
        intent.putExtra("user_name",user_saved);
        startActivity(intent);
    }

    public void OpenLogIn(View view){
        TextView text = (TextView) findViewById(R.id.username_show);
        Button log_button = (Button)findViewById(R.id.but_log_in);
        if(log_check == "unfinished") {
            Intent intent = new Intent(this, Logon_Or_Reg.class);
            if(user_saved!=""){
                //we got user info, can auto log in
                SharedPreferences myp = getSharedPreferences(PREF_N, MODE_PRIVATE);
                pass_saved =  myp.getString("user_password","");
                email_saved = myp.getString("user_email","");
            }
            Log.v("weird", Integer.toString(user_saved.length()));
            text.setText(user_saved);
            intent.putExtra("user_name", user_saved);
            intent.putExtra("password", pass_saved);
            intent.putExtra("email",email_saved);
            startActivity(intent);
        }
        else{
            //Using as log off
            resetpref();
            text.setText("Welcome Guest");
            log_button.setText("Log In");
            user_saved = "";
            pass_saved = "";
            email_saved = "";
            log_check = "unfinished";
        }
    }

    public void OpenShare(View view){
        Intent intent = new Intent(this,Share_Input.class);
        intent.putExtra("user_name",user_saved);
        startActivity(intent);
    }

    public void OpenMoments(View view){
        Intent intent = new Intent(this,MomentItemListActivity.class);
        intent.putExtra("user_name",user_saved);
        intent.putExtra("from_act","Main_Act");
        startActivity(intent);
    }

    public void OpenTracking(View view){
        Intent intent = new Intent(this,Tracking_map.class);
        intent.putExtra("user_name",user_saved);
        startActivity(intent);
    }

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

    public void location_click(){
        Intent intent = new Intent(this,Tracking_map.class);
        startActivity(intent);
    }
}
