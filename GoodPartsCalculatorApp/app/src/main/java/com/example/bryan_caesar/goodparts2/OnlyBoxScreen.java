package com.example.bryan_caesar.goodparts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnlyBoxScreen extends AppCompatActivity
{
    //Value to indicate whether or not only one box/bag was worked on during a shift
    //If the value is true, then "Yes" was selected
    //If the value is false, then "No" was selected
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static boolean isOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_only);

        //Button for "Yes"
        Button yes = findViewById(R.id.yesButton);
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                isOnly = true;
                nextActivity();
            }
        });

        //Button for "No"
        Button no = findViewById(R.id.noButton);
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                isOnly = false;
                nextActivity();
            }
        });
    }

    private void nextActivity()
    {
        Class nextClassFile;

        //If counting by "Weight" (regardless of how many boxes were worked on).
        if (MainActivity.getIsWeightCount())
            nextClassFile = FullWeight.class;
        //If counting by "# of Parts" and only one box was worked on.
        else if (!MainActivity.getIsWeightCount() && isOnly)
        {
            nextClassFile = StartNumber.class;
        }
        //If counting by "# of Parts" and more than one box was worked on.
        else
            nextClassFile = PartsInFull.class;

        Intent next = new Intent(getApplicationContext() , nextClassFile);
        startActivity(next);
    }

    //Returns the the value that represents whether or not only one box was worked on
    //If the value cannot be returned, then "false" will be returned by default
    //The default value is "false", since it is rare that only box is worked on
    public static boolean getIsOnly()
    {
        try
        {
            return isOnly;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
