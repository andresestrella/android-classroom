package com.example.myfirstapp;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private EditText nameEdit, lastNameEdit;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView date;
    private CheckBox mJavaCheck, mJsCheck, mPythonCheck, mCppCheck, mCsharpCheck, mGoCheck;
    private ArrayList<String> mResult;
    private Button clearButton, sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = findViewById(R.id.editText);
        lastNameEdit = findViewById(R.id.editText2);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        date = findViewById(R.id.editTextDate);

        radioGroup = findViewById(R.id.radio_group);
        radioButton = findViewById(R.id.radioButton);

        mJavaCheck = findViewById(R.id.check_java);
        mJsCheck = findViewById(R.id.check_js);
        mPythonCheck = findViewById(R.id.check_python);
        mCppCheck = findViewById(R.id.check_c);
        mCsharpCheck = findViewById(R.id.check_cs);
        mGoCheck = findViewById(R.id.check_go);
        mResult = new ArrayList<>();

        clearButton = findViewById(R.id.button_send);
        sendButton = findViewById(R.id.button_clear);

        mJavaCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mJavaCheck.isChecked())
                    mResult.add("Java");
                else
                    mResult.remove("Java");
            }
        });

        mJsCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mJsCheck.isChecked())
                    mResult.add("Javascript");
                else
                    mResult.remove("Javascript");
            }
        });

        mPythonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPythonCheck.isChecked())
                    mResult.add("Python");
                else
                    mResult.remove("Python");
            }
        });

        mCppCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCppCheck.isChecked())
                    mResult.add("C/C++");
                else
                    mResult.remove("C/C++");
            }
        });

        mCsharpCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCsharpCheck.isChecked())
                    mResult.add("C#");
                else
                    mResult.remove("C#");
            }
        });

        mGoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoCheck.isChecked())
                    mResult.add("Golang");
                else
                    mResult.remove("Golang");
            }
        });
    }


    //Called when the user taps the Send button
    public void submitForm(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //nameEdit = (EditText) findViewById(R.id.editText);
        //EditText editText2 = (EditText) findViewById(R.id.editText2);

        String message = "Hola!, mi nombre es: ";
        message += nameEdit.getText().toString() + " ";
        message += lastNameEdit.getText().toString();
        ArrayList<String> mResult = this.mResult;
        for (String s : mResult)
                message.concat(s + ',');
        message = message.substring(0, message.length()-1) + ".";
        intent.putExtra(EXTRA_MESSAGE, message);

        String message2 = "Soy " + spinner.getSelectedItem().toString().toLowerCase() + " y naci en fecha: "+ date.getText();

        String message3;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton){
            message3 = "Me gusta programar. Mis lenguajes favoritos son: ";
        }else
            message3 = "No me gusta programar.";

        intent.putExtra("com.example.myfirstapp.MESSAGE2", message2);
        intent.putExtra("com.example.myfirstapp.MESSAGE3", message3);
        startActivity(intent);
    }

    public void clickClear(View view){
        nameEdit.setText("");
        lastNameEdit.setText("");
        date.setText("");
        spinner.setSelection(0);
        radioGroup.clearCheck();

        mJavaCheck.setChecked(false);
        mCppCheck.setChecked(false);
        mCsharpCheck.setChecked(false);
        mGoCheck.setChecked(false);
        mJsCheck.setChecked(false);
        mPythonCheck.setChecked(false);
    }

}