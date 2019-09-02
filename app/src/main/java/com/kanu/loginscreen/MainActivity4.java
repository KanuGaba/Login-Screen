package com.kanu.loginscreen;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import java.util.Iterator;
        import java.util.LinkedList;

/**
 * Created by 2017kgaba on 10/26/2015.
 */
public class MainActivity4 extends Activity implements View.OnClickListener{
    LinkedList<Integer> list;
    private TextView scores;
    private Button button8;
    private SharedPreferences.Editor editor;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        button8 = (Button) this.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        mSettings = MainActivity4.this.getSharedPreferences("Settings", 0);
        editor = mSettings.edit();
        list = new LinkedList<Integer>();
        for(int x = 0; x < 10; x++)
            list.add(x, -1);
        scores = (TextView)findViewById(R.id.scores);
        if(mSettings.getString("str", "missing") != "missing") {
            LinkedList<Integer> temp = new LinkedList<Integer>();
            String values = mSettings.getString("str", "missing");
            for(int x = 0; x < values.length(); x++) {
                if(values.charAt(x) == '-'){
                    temp.add(Integer.parseInt(values.charAt(x) + values.charAt(x+1) + ""));
                    x++;
                }
                if (Character.isDigit(values.charAt(x))) {
                    temp.add(Integer.parseInt(values.charAt(x) + ""));
                }
            }
            list = temp;
        }
        editor.apply();
        Bundle bundle = getIntent().getExtras();
        updateScore(bundle.getInt("wins"));
        printScores();

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
        if (v == button8) {
            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(intent);
        }
    }

    public void updateScore(int wins){
        //Log.v("hi", list.size() + "");
        if(list.get(0) == -1)
            list.add(0, wins);
        else
            for (int i = 0; i < list.size(); i++) {
               if(list.get(i) != -1) {
                    if (wins <= list.get(i))
                        list.add(i, wins);
                    else if(list.get(i + 1) == -1 && (i + 1) < 10)
                        list.add(i + 1, wins);
                }
            }
    }

    public void printScores(){
        for (int i = 0; i < 10; i++) {
            if(list.get(i) == -1)
                scores.setText(scores.getText().toString() + (i + 1) + ". " + "\n");
            else
                scores.setText(scores.getText().toString() + (i + 1) + ". " + list.get(i) + "\n");
        }
        editor.putString("str", list.toString());
        editor.commit();
    }
}
