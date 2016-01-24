package com.example.marjoriepickard.passwordgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Global Variables
    Button btnGenerate;
    SeekBar lengthSeekBar;
    TextView lengthTextView;
    TextView passwordTextView;
    CheckBox capLetCheckBox;
    CheckBox numCheckBox;
    CheckBox symTextBox;
    CheckBox lowerCheckBox;
    boolean numChecked = false;
    boolean symChecked = false;
    boolean lowerChecked = false;
    boolean upperChecked = true;
    int passLength;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the button/bar/checkboxes/text
        lengthSeekBar = (SeekBar) findViewById(R.id.lengthSeekBar);
        lengthTextView = (TextView) findViewById(R.id.lengthTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        capLetCheckBox=(CheckBox)findViewById(R.id.capLetCheckBox);
        lowerCheckBox=(CheckBox)findViewById(R.id.lowerCheckBox);
        numCheckBox=(CheckBox)findViewById(R.id.numCheckBox);
        symTextBox=(CheckBox)findViewById(R.id.symCheckBox);
        btnGenerate= (Button)findViewById(R.id.btnGenerate);

        passwordTextView.setText("Password Here");


        //Showing the password length initially and setting the initial password length to 8 characters
        lengthTextView.setText("Password Length: " + (lengthSeekBar.getProgress() + 8));
        passLength = 8;

        //waits for the user to change the password length slider
        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override

            //as the slider is moved it updates the length of the password
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passLength = progress + 8;  //change the value of the password length
                lengthTextView.setText("Password Length: " + passLength);  //Change the shown value of the password length
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Not used
            }
        });

        //When the Generate Password button is clicked
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if no checkboxes are checked, use only capital letters in the password
                if (!upperChecked && !lowerChecked && !numChecked && !symChecked) {
                    capitalsByDefault();
                }

                //generate the password
                password = passwordGenerate();


                //Show Password
                passwordTextView.setText("" + password);
            }
        });


    }

    public String passwordGenerate () {

        //Arrays of the characters arranged by type
        //TODO:
        char[] charNum = "0123456789".toCharArray();
        char[] charCap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] charLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] charSym = "~`!@#$%^&*()-_=+[{]};:\\|'\",<.>/?".toCharArray();

        StringBuilder characters = new StringBuilder();
        if (upperChecked)
            characters.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (lowerChecked)
            characters.append("abcdefghijklmnopqrstuvwxyz");
        if (numChecked)
            characters.append("01234567890123456789");
        if(symChecked)
            characters.append("~`!@#$%^&*()-_=+[{]};:\\|'\",<.>/?");

        String str = characters.toString();
        char[] chars = str.toCharArray();

        boolean numPresent = false;
        boolean capitalPresent = false;
        boolean lowercasePresent = false;
        boolean symbolPresent = false;


        StringBuilder password = new StringBuilder();
        Random random1 = new Random();

        //add characters to password one character at a time
        int i = 1;
        while (i <= passLength) {
            char ci = chars[random1.nextInt(chars.length)];
            //Toast.makeText(MainActivity.this, "i: " + i + ci, Toast.LENGTH_SHORT).show();
            password.append(ci);

            //Booleans to check if the different types of characters are present
            String charString = Character.toString(ci);  //convert from character to string
            if (new String(charNum).contains(charString)) {
                numPresent = true;
            }
            if (new String(charCap).contains(charString)) {
                capitalPresent = true;

            }
            if (new String(charLower).contains(charString)) {
                lowercasePresent = true;
            }
            if (new String(charSym).contains(charString)) {
                symbolPresent = true;
            }

            //Check to see if values are present but not wanted
            if (i == passLength) {
                if (!capitalPresent && upperChecked) {
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!lowercasePresent && lowerChecked) {
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!numPresent && numChecked) {
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!symbolPresent && symChecked) {
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }
            }

            i++;
        }
        //Show password
        return (password.toString());
    }

    public void capitalsByDefault() {
        //pretend that the user wanted capital leters
        upperChecked = true;
        //check the box in the UI
        capLetCheckBox.setChecked(true);
    }

    public void onCheckboxClicked (View view) {
        //Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        /*check which checkbox was clicked and if it was checked,
        set the corresponding boolean to true, if it was unchecked, set the boolean to false*/
        switch(view.getId()) {
            case R.id.capLetCheckBox:
                if (checked)
                    upperChecked = true;
                else
                    upperChecked = false;
                break;
            case R.id.lowerCheckBox:
                if(checked)
                    lowerChecked = true;
                else
                    lowerChecked = false;
                break;
            case R.id.numCheckBox:
                if (checked)
                    numChecked = true;
                else
                    numChecked = false;
                break;
            case R.id.symCheckBox:
                if (checked)
                    symChecked = true;
                else
                    symChecked = false;
                break;

        }
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
}
