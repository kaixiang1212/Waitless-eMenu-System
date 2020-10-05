package com.example.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
    This class is the android activity that enables the setup of the table with a user provided table number
    to help restaurant staff assign the tablet to a table.
 */
public class TableSetupActivity extends AppCompatActivity {

    /**
     * This overridden method runs the customised setup processes on the creation of this activity(during app startup)
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_setup);
        final EditText tableInput = (EditText) findViewById(R.id.table_number_input);
        final TextView testTextView = findViewById(R.id.textView9);
        Button submitButton = (Button) findViewById(R.id.table_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String testStr = tableInput.getText().toString();
                TableManager.setTableNo(Integer.valueOf(testStr));
                startActivity(new Intent(TableSetupActivity.this, MainActivity.class));
            }
        });
    }
}
