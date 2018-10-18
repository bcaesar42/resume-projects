package com.example.bryan_caesar.goodparts2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class FullWeight extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static float fullWeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_weight);

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.fullWeightInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.fullWeightButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    fullWeightValue = Utils.getValue(editText);
                    if (fullWeightValue < 0)
                        throw new IllegalArgumentException("Cannot be negative");
                    nextActivity();
                }
                catch (Exception e)
                {
                    Utils.handleInvalidInput(editText , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the weight of a full box
    //If the value cannot be returned, then "1" will be returned (to prevent dividing by zero).
    public static float getFullWeight()
    {
        try
        {
            return fullWeightValue;
        }
        catch (Exception e)
        {
            return 1;
        }
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , StartWeight.class);
        startActivity(next);
    }

}
