package team10.cs442.project.com.Log_Libs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team10.cs442.project.com.MyLog;
import team10.cs442.project.com.R;
import team10.cs442.project.com.database_libs.Databasehelper;

public class Add_Log extends AppCompatActivity {
    Databasehelper myDb;
    EditText text1;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_log);
        myDb = new Databasehelper(this);
        text1 = (EditText)findViewById(R.id.textView);
        //text1 = (EditText)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        AddData();

    }

    public void AddData(){
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean inInserted =  myDb.insertData(text1.getText().toString());
                        if(inInserted == true)
                            Log.d("Item ", "Inserted");
                        Toast.makeText(getBaseContext(), "Log added Successfully", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(getBaseContext(),MyLog.class);
                        startActivity(in);
                    }
                }
        );
    }
}
