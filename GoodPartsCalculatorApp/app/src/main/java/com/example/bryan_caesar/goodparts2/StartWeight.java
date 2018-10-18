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

public class StartWeight extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static float startWeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_weight);

        //If this is the only box that was worked on, then this will
        //slightly change the wording of the prompt
        if (OnlyBoxScreen.getIsOnly())
        {
            TextView prompt = (TextView) findViewById(R.id.startWeightPrompt);
            prompt.setText("Enter the box's starting weight");
        }

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.startWeightInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.startWeightButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    startWeightValue = Utils.getValue(editText);
                    if (startWeightValue < 0 || startWeightValue > FullWeight.getFullWeight())
                        throw new IllegalArgumentException("Cannot be negative");
                    nextActivity();
                }
                catch (Exception e)
                {
                    determineError(editText , FullWeight.getFullWeight() , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the starting weight
    //If the value cannot be returned, then "0" will be returned
    public static float getStartWeight()
    {
        try
        {
            return startWeightValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , EndWeight.class);
        startActivity(next);
    }

    //If the starting weight that was entered is greater than the weight needed to fill a box,
    //then this method will call another private method that was designed to handle that specific case.
    //Otherwise, it will call the Utils.handleInvalidInput method and pass along the necessary
    //information for that method to handle the problem.
    private static void determineError(EditText inputBox , final float maxValue , final Context applicationContext)
    {
        try
        {
            float test = Float.parseFloat(inputBox.getText().toString());
            if (test > maxValue)
            {
                throw new NotLogicalWeightException("Starting weight cannot exceed the weight of a full box.");
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        catch (NotLogicalWeightException notLogExcept)
        {
            handleStartTooBig(inputBox , maxValue , applicationContext);
        }
        catch (Exception e)
        {
            Utils.handleInvalidInput(inputBox , applicationContext);
        }
    }

    private static void handleStartTooBig(EditText inputBox , final float maxValue , final Context applicationContext)
    {
        String message = "Starting weight cannot be greater than the weight needed to fill a box.\n(In this case: " + maxValue + ")";

        //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
        Toast toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        Utils.showError(toast);
        inputBox.setText("");
    }

}
