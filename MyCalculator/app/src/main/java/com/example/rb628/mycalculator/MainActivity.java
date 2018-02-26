package com.example.rb628.mycalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView m_display;
    private SharedPreferences mSP;
    private boolean mDoVibrate = false;
    private Vibrator mV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mV = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        m_display = findViewById(R.id.txtDisplay);

        // Log.i("CALCULATOR", "Called onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();

        mDoVibrate = mSP.getBoolean("vibrate", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            Intent intent = new Intent(this, CalcPreferencesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClicked(View view){
        Button button = (Button)view;
        String text, result;

        switch (view.getId()){
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
                m_display.append(button.getText());
                break;
            case R.id.btnPlus:
            case R.id.btnMinus:
                text = m_display.getText().toString();
                if (!text.endsWith("+") && !text.endsWith("-") && !text.isEmpty()) {
                    m_display.append(button.getText());
                }
                break;
            case R.id.btnDel:
                text = m_display.getText().toString();
                if (!text.isEmpty()){
                    m_display.setText(text.substring(0, text.length()-1));
                }
                break;
            case R.id.btnClear:
                m_display.setText("");
                break;
            case R.id.btnSum:
                    result = evalExpression(m_display.getText().toString());
                    m_display.setText(result);
                    break;
        }


        if (mDoVibrate){
            mV.vibrate(500);
            Toast.makeText(getApplicationContext(), "Vibrate ...", Toast.LENGTH_SHORT).show();
        }
    }

    private  String evalExpression(String expr){
        boolean doAdd = true;
        BigInteger result;
        result = BigInteger.ZERO;
        StringTokenizer st = new StringTokenizer(expr, "+-", true);
        while(st.hasMoreElements()){
            String token = st.nextToken();
            Log.i("CALCULATOR", token);

            if (token.equals("+")){
                doAdd = true;
            }
            else if (token.equals("-")){
                doAdd = false;
            }
            else{
                BigInteger value = new BigInteger(token);
                if (doAdd){
                    result = result.add(value);
                }
                else{
                    result = result.subtract(value);
                }
            }
        }
        return result.toString();
    }
}
