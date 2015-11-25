package team10.cs442.project.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import team10.cs442.project.com.MainActs.MainAct;

public class Logon_Or_Reg extends Activity {

    Button msign_up;
    Button mlog_in;
    EditText mEmail;
    EditText mUsr;
    EditText mPas;

    String user_names = "";
    String emails = "";
    String passwords = "";
    Boolean have_saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon__or__reg);

        msign_up = (Button)findViewById(R.id.submit_sign_up);
        mlog_in = (Button)findViewById(R.id.submit_logIn_info);
        mEmail = (EditText)findViewById(R.id.email);
        mUsr   = (EditText)findViewById(R.id.username);
        mPas   = (EditText)findViewById(R.id.password);
        Bundle extras = getIntent().getExtras();

        try_set_info(extras);

        msign_up.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        debug_print();
                        if (have_saved == false) {
                            user_names = mUsr.getText().toString();
                            emails = mEmail.getText().toString();
                            passwords = mPas.getText().toString();
                            Log.v("set_info", "sign up");
                        }
                        sign_up();
                        go_to_main();
                    }
                });

        mlog_in.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        debug_print();
                        if (have_saved == false) {
                            user_names = mUsr.getText().toString();
                            emails = mEmail.getText().toString();
                            passwords = mPas.getText().toString();
                            Log.v("set_info", "log in");
                        }
                        log_in();
                        go_to_main();
                    }
                });

    }

    public void try_set_info(Bundle extras){
        user_names = extras.getString("user_name");
        Log.v("try_set_info user_names", Integer.toString(user_names.length()));
        if(user_names.length() == 0){
            Log.v("test", "no");
            have_saved = false;
        }
        else{
            //have a saved user
            Log.v("try_set_info", "error");
            emails = extras.getString("email","");
            passwords = extras.getString("password","");
            have_saved = true;
        }
    }

    public void debug_print() {
        Log.v("EditText", user_names);
        Log.v("EditText", emails);
        Log.v("EditText", passwords);
        Toast.makeText(getApplicationContext(), "submitted",Toast.LENGTH_LONG).show();
    }

    public void go_to_main(){
        Intent intent = new Intent(this, MainAct.class);
        if(have_saved){
            startActivity(intent);
        }
        intent.putExtra("from_act","Logon_Or_Reg");
        intent.putExtra("username", user_names);
        Log.v("intent send", user_names);
        intent.putExtra("password", emails);
        Log.v("intent send", emails);
        intent.putExtra("email", passwords);
        Log.v("intent send", passwords);
        intent.putExtra("initialized", "finished");
        startActivity(intent);
    }

    public void log_in(){

        ParseUser.logInInBackground(mUsr.getText().toString(), mPas.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, com.parse.ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Toast.makeText(getApplicationContext(), "Welcome back " + mUsr.getText().toString(),Toast.LENGTH_LONG).show();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast.makeText(getApplicationContext(), "Opps" + e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sign_up(){
        ParseUser user = new ParseUser();
        user.setUsername(user_names);
        user.setPassword(passwords);
        user.setEmail(emails);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Thanks for signing up!",Toast.LENGTH_LONG).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(getApplicationContext(), "Opps" + e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logon__or__reg, menu);
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
