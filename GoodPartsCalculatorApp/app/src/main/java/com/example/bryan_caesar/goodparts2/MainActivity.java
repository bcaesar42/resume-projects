package com.example.bryan_caesar.goodparts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    //Value to indicate which counting method is selected
    //If the value is true, then "Weight" was selected
    //If the value is false, then "# of Parts" was selected
    //The value is static so that a "MainActivity" object will not have to be instanced in later class files
    //The value is private in order to prevent it from being changed externally
    //However, the value can still be seen via a public "get" method
    private static boolean isWeightCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button for "Weight"
        Button weight_buton = findViewById(R.id.weightButton);
        weight_buton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                isWeightCount = true;
                nextActivity();
            }
        });

        //Button for "# of Parts"
        Button number_button = findViewById(R.id.numberButton);
        number_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                isWeightCount = false;
                nextActivity();
            }
        });
    }

    private void nextActivity()
    {
        Intent next = new Intent(getApplicationContext() , OnlyBoxScreen.class);
        startActivity(next);
    }

    //Returns the the value that represents which counting method is being used
    //"true" for "Weight" and "false" for "# of Parts"
    //If the value cannot be returned, then "true" will be returned by default
    //The default value is "true", since weight counting is the method that operators are most likely to need help with
    public static boolean getIsWeightCount()
    {
        try
        {
            return isWeightCount;
        }
        catch (Exception e)
        {
            return true;
        }
    }
}
