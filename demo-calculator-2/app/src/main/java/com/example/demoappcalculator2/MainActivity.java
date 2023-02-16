package com.example.demoappcalculator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    TextView result, subResult;
    MaterialButton btnDiv, btnMultiply, btnMinus, btnPlus, btnEqual;
    MaterialButton btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnAC;
    MaterialButton btnDot, btnOpenBracket, btnCloseBracket;
    Button btnViewHistory;
    String calculateData = "";
    String calculateDataStr;

    protected  void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(subResult.getText().toString() != null) {
            outState.putString("subResult", subResult.getText().toString());
        }
        if(result.getText().toString() != null) {
            outState.putString("result", result.getText().toString());
        }
    }

    protected  void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.get("subResult") != null) {
            subResult.setText(savedInstanceState.get("subResult").toString());
        }
        if(savedInstanceState.get("result") != null) {
            result.setText(savedInstanceState.get("result").toString());
        }
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            MaterialButton button = (MaterialButton) view;
            String btnText = button.getText().toString();
            String calculateStr = subResult.getText().toString();

            if(btnText.equals("AC")) {
                subResult.setText("");
                result.setText("0");
                return;
            }else if(btnText.equals("=")) {
                calculateData = calculateStr;
                String finalResult = calcualate(calculateStr);
                calculateData += " = " + finalResult;
                calculateDataStr += calculateData + '\n';
                if(!finalResult.equals("Error")) {
                    result.setText(finalResult);
                }
                return;
            } else {
                calculateStr += btnText;
            }
            subResult.setText(calculateStr);

        }
    };

    public void assignButton (MaterialButton button, int id) {
        button = findViewById(id);
        button.setOnClickListener(buttonClick);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignButton(btnDiv, R.id.buttonDiv);
        assignButton(btnMultiply, R.id.buttonMultiply);
        assignButton(btnMinus, R.id.buttonMinus);
        assignButton(btnPlus, R.id.buttonPlus);
        assignButton(btnEqual, R.id.buttonEqual);
        assignButton(btnZero, R.id.buttonZero);
        assignButton(btnOne, R.id.buttonOne);
        assignButton(btnTwo, R.id.buttonTwo);
        assignButton(btnThree, R.id.buttonThree);
        assignButton(btnFour, R.id.buttonFour);
        assignButton(btnFive, R.id.buttonFive);
        assignButton(btnSix, R.id.buttonSix);
        assignButton(btnSeven, R.id.buttonSeven);
        assignButton(btnEight, R.id.buttonEight);
        assignButton(btnNine, R.id.buttonNine);
        assignButton(btnDot, R.id.buttonDot);
        assignButton(btnOpenBracket, R.id.buttonOpenBracket);
        assignButton(btnCloseBracket, R.id.buttonCloseBracket);
        assignButton(btnAC, R.id.buttonAC);
        result = findViewById(R.id.result);
        subResult = findViewById(R.id.sub_result);
        btnViewHistory = findViewById(R.id.btnViewHis);


        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calculateData == "") {
                    System.out.println("Missing input parameter");
                }else {
                    Intent intent = new Intent(getApplicationContext(), ViewHisActivity.class);
                    if(calculateDataStr.contains("null")) {
                        calculateDataStr = calculateDataStr.substring(4);
                    }
                    intent.putExtra("calculateDataStr", calculateDataStr);
                    startActivity(intent);
                }
            }
        });
    }

    String calcualate(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            return result;
        }catch (Exception e) {
            return "Error";
        }
    }
}