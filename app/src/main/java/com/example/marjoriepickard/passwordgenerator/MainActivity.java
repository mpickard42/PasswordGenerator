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
import android.widget.Toast;
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
        //TODO: refine algorithm

        /*Arrays of the characters arranged by type and one large one combined
        (Could be refined, storing the array somewhere else? adding the smaller type arrays together based on what the user wants?*/
        char[] charNum = "0123456789".toCharArray();
        char[] charCap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] charLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] charSym = "~`!@#$%^&*()-_=+[{]};:\\|'\",<.>/?".toCharArray();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345678901234567890~`!@#$%^&*()-_=+[{]};:\\|'\",<.>/?".toCharArray();

        /**************************************************************
        These are booleans to determine if a character has been put into the string that will become the password.
        for example: if a number is added to the string, the numPresent boolean will be set to true.
        I imagined that after the string has been built, If statements could be used to determine if the values of the
        booleans below matched what the user wanted.  So if a user wants numbers, the numPresent boolean must match the
        numChecked boolean which is determined by what checkboxes the user picks. Otherwise the password will be reset
        and the password process starts over.  This is where it usually breaks.  It is works almost perfectly if I ask it to reset the
        password and start over if it is searching for an unwanted character.  The code for that is below.
        ex.
        if (new String(charNum).contains(charString) && !numChecked) {
            password.setLength(0);
            i = 0;
            }
        if (new String(charCap).contains(charString) && !upperChecked) {
                    password.setLength(0);
            i = 0;
            }
        if (new String(charLower).contains(charString) && !lowerChecked) {
            password.setLength(0);
            i = 0;
            }
        if (new String(charSym).contains(charString) && !symChecked) {
            password.setLength(0);
            i = 0;
            }

        However, this only makes sure that a no unwanted character make it into the password, not that the password
        contains the wanted characters

        I have tried using while loops and for loops, neither works.
         ****************************************************************************************/
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
                //Toast.makeText(MainActivity.this, "number found", Toast.LENGTH_SHORT).show();
                numPresent = true;
            }
            if (new String(charCap).contains(charString)) {
                //Toast.makeText(MainActivity.this, "capitals present", Toast.LENGTH_SHORT).show();
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
                    Log.d("Test", "Capitals wanted but not present");
                    password.setLength(0);
                    i = 0; //Setting the i to 0 is what breaks it, but dont know why!!!!
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!lowercasePresent && lowerChecked) {
                    Log.d("Test", "Lowercase wanted but not present");
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!numPresent && numChecked) {
                    Log.d("Test", "Numbers wanted but not present");
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (!symbolPresent && symChecked) {
                    Log.d("Test", "Symbols wanted but not present");
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }
                if (capitalPresent && !upperChecked) {
                    Log.d("Test", "Capitals present but not wanted");
                    password.setLength(0);
                    i = 0; //Setting the i to 0 is what breaks it, but dont know why!!!!
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (lowercasePresent && !lowerChecked) {
                    Log.d("Test", "Lowercase present but not wanted");
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (numPresent && !numChecked) {
                    Log.d("Test", "Numbers present but not wanted");
                    password.setLength(0);
                    i = 0;
                    numPresent = false;
                    capitalPresent = false;
                    lowercasePresent = false;
                    symbolPresent = false;
                }

                if (symbolPresent && !symChecked) {
                    Log.d("Test", "Symbols present but not wanted");
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
                if (checked){
                    upperChecked = true;
                }
                else {
                    upperChecked = false;
                    Log.i("Testing", "Capitals wanted");
                }
                break;
            case R.id.lowerCheckBox:
                if(checked) {
                    lowerChecked = true;
                }
                else {
                    lowerChecked = false;
                }
                break;
            case R.id.numCheckBox:
                if (checked){
                    numChecked = true;
                }
                else {
                    numChecked = false;
                }
                break;
            case R.id.symCheckBox:
                if (checked){
                    symChecked = true;
                }
                else {
                    symChecked = false;
                }
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
