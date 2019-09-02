package com.kanu.loginscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2017kgaba on 10/26/2015.
 */
public class MainActivity2 extends Activity implements View.OnClickListener{
    private Button button, back;
    private EditText username, password, confirm;
    private TextView error;
    private Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.signup_screen);
        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);
        back = (Button) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        username = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText4);
        confirm = (EditText)findViewById(R.id.editText5);
        error = (TextView)findViewById(R.id.textView9);
        myFirebaseRef = new Firebase("https://shining-torch-2195.firebaseio.com/");
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

    public void onClick(View v) {
        if (v == button) {
            if (password.getText().toString().equals(confirm.getText().toString())) {
                myFirebaseRef.createUser(username.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        error.setText("Account Created!");
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        error.setText(firebaseError.getMessage());
                    }
                });
            }
            else {
                error.setText("Passwords do not match.");
            }
        }

        if (v == back) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
