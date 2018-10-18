package com.example.bryan_caesar.goodparts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PartsInFull extends AppCompatActivity
{
    //Value that holds the input from the EditText box
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static int partsInBoxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_in_full);

        //Creates an EditText java object based on the id of the input box.
        final EditText editText = findViewById(R.id.partsInFullInputBox);

        //"Enter" button
        Button enter = findViewById(R.id.partsInFullButton);
        enter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    partsInBoxValue = Utils.getValueInt(editText);
                    if (partsInBoxValue < 1)
                        throw new IllegalArgumentException("Must be at least 1.");
                    nextActivity();
                }
                catch (Exception e)
                {
                    Utils.handleInvalidInputInt(editText , 1 , getApplicationContext());
                }
            }
        });
    }

    //Returns the the value that was entered for the number of parts in a full box
    //If the value cannot be returned, then "0" will be returned
    public static int getPartsInFull()
    {
        try
        {
            return partsInBoxValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private void nextActivity()
    {
        Class nextClassFile;

        //If counting by "Weight" and more than one box was worked on.
        if (MainActivity.getIsWeightCount() && !OnlyBoxScreen.getIsOnly())
            nextClassFile = BoxesFilled.class;
        //If counting by "Weight" and only one box was worked on.
        else if (MainActivity.getIsWeightCount() && OnlyBoxScreen.getIsOnly())
        {
            nextClassFile = Result.class;
        }
        //If counting by "# of Parts".
        else
            nextClassFile = StartNumber.class;

        Intent next = new Intent(getApplicationContext() , nextClassFile);
        startActivity(next);
    }

}
