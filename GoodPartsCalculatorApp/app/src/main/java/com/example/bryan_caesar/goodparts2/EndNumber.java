package com.example.bryan_caesar.goodparts2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EndNumber extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static int endNumberValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_number);

        //If this is the only box that was worked on, then this will
        //slightly change the wording of the prompt
        if (OnlyBoxScreen.getIsOnly())
        {
            TextView prompt = (TextView) findViewById(R.id.endNumberPrompt);
            prompt.setText("How many parts are in the box at the end?");
        }

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.endNumberInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.endNumberButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    endNumberValue = Utils.getValueInt(editText);

                    //Checks whether or not the ending parts count being entered is greater than
                    //the number of parts in a full box, but only if multiple boxes were worked on.
                    //This check is only performed if multiple boxes were worked on because otherwise
                    //PartsInFull is skipped and PartsInFull.getPartsInFull() defaults to zero.
                    if (endNumberValue > PartsInFull.getPartsInFull() && !OnlyBoxScreen.getIsOnly())
                        throw new NotLogicalNumberException("Ending parts cannot exceed parts in full box.");

                    //Ensures the input is not negative.
                    if (endNumberValue < 0)
                        throw new IllegalArgumentException("Cannot be negative");

                    //If this box was the only box being worked on, this ensures that the ending
                    //number is not less than the starting number.
                    if (endNumberValue < StartNumber.getStartNumber() && OnlyBoxScreen.getIsOnly())
                        throw new IllegalArgumentException("If only one box was worked on, then the ending number cannot be less than the starting number.");

                    //This makes the app go to the next screen.
                    //However, this line will not execute if the input was deemed invalid by one
                    //of the previous "if" statements in this "try" block. If the input is deemed
                    //invalid, the program would jump to the appropriate "catch" block as soon as
                    //an exception is thrown, thus skipping this line.
                    nextActivity();
                }
                catch (NotLogicalNumberException notLog)
                {
                    handleEndTooBig(editText , PartsInFull.getPartsInFull() , getApplicationContext());
                }
                catch (Exception e)
                {
                    //If only one box is worked on, this will ensure that the ending number is not less than the starting number of that same box.
                    if (OnlyBoxScreen.getIsOnly())
                        Utils.handleInvalidInputInt(editText , StartNumber.getStartNumber() , "the starting number." , getApplicationContext());
                    else
                        Utils.handleInvalidInput(editText , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the number of parts in the ending box.
    //If the value cannot be returned, then "0" will be returned
    public static int getEndNumber()
    {
        try
        {
            return endNumberValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Intent next;

        if (OnlyBoxScreen.getIsOnly())
            next = new Intent(getApplicationContext() , Result.class);
        else
            next = new Intent(getApplicationContext() , BoxesFilled.class);
        startActivity(next);
    }

    private static void handleEndTooBig(EditText inputBox , final int maxValue , final Context applicationContext)
{
    String message = "Ending amount cannot exceed the number of parts needed to fill a box.\n(In this case: " + maxValue + ")";

    //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
    Toast toast = Toast.makeText(applicationContext , message , Toast.LENGTH_LONG);
    toast.setGravity(Gravity.CENTER, 0 , 0);
    Utils.showError(toast);
    inputBox.setText("");
}
}
