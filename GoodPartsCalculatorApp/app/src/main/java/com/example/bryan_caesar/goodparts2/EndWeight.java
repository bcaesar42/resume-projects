package com.example.bryan_caesar.goodparts2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class EndWeight extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static float endWeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_weight);

        //If this is the only box that was worked on, then this will
        //slightly change the wording of the prompt
        if (OnlyBoxScreen.getIsOnly())
        {
            TextView prompt = (TextView) findViewById(R.id.endWeightPrompt);
            prompt.setText("Enter the box's ending weight");
        }

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.endWeightInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.endWeightButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    endWeightValue = Utils.getValue(editText);
                    if (endWeightValue < 0)
                        throw new IllegalArgumentException("Cannot be negative");
                    if (endWeightValue > FullWeight.getFullWeight())
                        throw new NotLogicalWeightException("End amount cannot exceed the amount of a full box.");
                    if (endWeightValue < StartWeight.getStartWeight() && OnlyBoxScreen.getIsOnly())
                        throw new IllegalArgumentException("If only one box was worked on, then the ending weight cannot be less than the starting weight.");
                    nextActivity();
                }
                catch (NotLogicalWeightException notLog)
                {
                    handleEndTooBig(editText , FullWeight.getFullWeight() , getApplicationContext());
                }
                catch (Exception e)
                {
                    //If only one box is worked on, this will ensure that the ending weight is not less than the starting weight of that same box.
                    if (OnlyBoxScreen.getIsOnly())
                        Utils.handleInvalidInput(editText , StartWeight.getStartWeight() , "the starting weight." , getApplicationContext());
                    else
                        Utils.handleInvalidInput(editText , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the ending weight
    //If the value cannot be returned, then "0" will be returned
    public static float getEndWeight()
    {
        try
        {
            return endWeightValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , PartsInFull.class);
        startActivity(next);
    }

    private static void handleEndTooBig(EditText inputBox , final float maxValue , final Context applicationContext)
    {
        String message = "Ending weight cannot exceed the weight needed to fill a box.\n(In this case: " + maxValue + ")";

        //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
        Toast toast = Toast.makeText(applicationContext , message , Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0 , 0);
        Utils.showError(toast);
        inputBox.setText("");
    }
}
