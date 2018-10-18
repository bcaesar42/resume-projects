package com.example.bryan_caesar.goodparts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BoxesFilled extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static int boxesFilledValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxes_filled);

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.boxesFilledInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.boxesFilledButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    boxesFilledValue = Utils.getValueInt(editText);
                    if (boxesFilledValue < 0)
                        throw new IllegalArgumentException("Cannot be negative");
                    nextActivity();
                }
                catch (Exception e)
                {
                    Utils.handleInvalidInputInt(editText , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the number of boxes filled from start to finish
    //If the value cannot be returned, then "0" will be returned
    public static int getBoxesFilled()
    {
        try
        {
            return boxesFilledValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , Result.class);
        startActivity(next);
    }
}
