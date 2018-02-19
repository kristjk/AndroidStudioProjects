package com.example.rb628.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView m_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_display = findViewById(R.id.txtDisplay);

        // Log.i("CALCULATOR", "Called onCreate");
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
