package team10.cs442.project.com.Log_Libs;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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

public class UpdateItemID extends AppCompatActivity {
    Databasehelper myDb;
    EditText text1,text2,text3;
    String id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_item_2);
        id = getIntent().getExtras().getString("ID");
        Log.d("Id is : ", id);
        myDb = new Databasehelper(this);
        Cursor res = myDb.getSpecificData(Integer.parseInt(id));
        text1 = (EditText)findViewById(R.id.textView9);
        Button but = (Button)findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
                Intent in = new Intent(getBaseContext(), MyLog.class);
                startActivity(in);
            }
        });
        if (res.getCount() == 0) {
            showMessage("No", "Data");
        }
        if (res.moveToFirst()) {
            do {
                String name;
                name = res.getString(1);
                Log.d("Name is :: ", name);
                text1.setText(name);
            } while (res.moveToNext());
        }
    }

    public void showMessage(String title,String message){
        Log.d(title, message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateItem(){
        myDb = new Databasehelper(this);
        String name = text1.getText().toString();
        int res = myDb.updateItem(id,name);
        Toast.makeText(this, "Updated Log ID is : " + id, Toast.LENGTH_LONG).show();
    }
}
