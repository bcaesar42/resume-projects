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

public class StartNumber extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static int startNumberValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_number);

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.startNumberInputBox);

        //If this is the only box that was worked on, then this will
        //slightly change the wording of the prompt
        if (OnlyBoxScreen.getIsOnly())
        {
            TextView prompt = (TextView) findViewById(R.id.startNumberPrompt);
            prompt.setText("How many parts were already in the box at the start?");
        }

        //"Enter" button
        Button enter = findViewById(R.id.startNumberButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    startNumberValue = Utils.getValueInt(editText);

                    //Prevents the starting number from being greater than the number needed to
                    //fill a box, but only if this box was not the only box worked one.
                    //If only one box is worked on, then PartsInFull is skipped. If PartsInFull
                    //is skipped, then PartsInFull.getPartsInFull() will always return zero. Thus,
                    //if only one box was worked on, any input greater than zero would not be accepted.
                    //Therefore, the starting number should only be compared against
                    //PartsInFull.getPartsInFull()if more than one box was worked on.
                    if (startNumberValue > PartsInFull.getPartsInFull() && !OnlyBoxScreen.getIsOnly())
                        throw new IllegalArgumentException("Invalid input.");

                    //Ensures the input is not negative.
                    if (startNumberValue < 0)
                        throw new IllegalArgumentException("Cannot be negative.");
                    nextActivity();
                }
                catch (Exception e)
                {
                    determineError(editText , PartsInFull.getPartsInFull() , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the number of parts already in the starting box.
    //If the value cannot be returned, then "0" will be returned
    public static int getStartNumber()
    {
        try
        {
            return startNumberValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , EndNumber.class);
        startActivity(next);
    }

    //If the starting amount that was entered is greater than the amount needed to fill a box,
    //then this method will call another private method that was designed to handle that specific case.
    //Otherwise, it will call the Utils.handleInvalidInputInt method and pass along the necessary
    //information for that method to handle the problem.
    private static void determineError(EditText inputBox , final int maxValue , final Context applicationContext)
    {
        try
        {
            int test = Integer.parseInt(inputBox.getText().toString());
            if (test > maxValue)
            {
                throw new NotLogicalNumberException("Starting quantity cannot exceed the quantity of a full box.");
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        catch (NotLogicalNumberException notLogExcept)
        {
            handleStartTooBig(inputBox , maxValue , applicationContext);
        }
        catch (Exception e)
        {
            Utils.handleInvalidInputInt(inputBox , applicationContext);
        }
    }

    private static void handleStartTooBig(EditText inputBox , final int maxValue , final Context applicationContext)
    {
        String message = "Starting amount cannot exceed the number of parts needed to fill a box.\n(In this case: " + PartsInFull.getPartsInFull() + ")";

        //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
        Toast toast = Toast.makeText(applicationContext , message , Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0 , 0);
        Utils.showError(toast);
        inputBox.setText("");
    }
}
