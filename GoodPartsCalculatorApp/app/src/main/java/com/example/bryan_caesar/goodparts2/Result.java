package com.example.bryan_caesar.goodparts2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Creates a "TextView" java object in order to manipulate the output box.
        TextView resultBox = findViewById(R.id.outputBox);

        //The following "if / else if / else" block will determine which path the app took
        //and will perform the appropriate calculations accordingly.
        //The TextView box in the middle will display the result.

        //Executes if the "Weight" counting method is being used and more than one box was worked on.
        //This is probably the most common option.
        if (MainActivity.getIsWeightCount() && !OnlyBoxScreen.getIsOnly())
        {
            resultBox.setText(calculateWeightAndNotOnly());
        }
        //Executes if the "# of Parts" method is being used and more than one box was worked on.
        //This is probably the second-most common option.
        else if (!MainActivity.getIsWeightCount() && !OnlyBoxScreen.getIsOnly())
        {
            resultBox.setText(calculateNumberAndNotOnly());
        }
        //Executes if the "Weight" counting method is being used and only one box was worked on.
        else if (MainActivity.getIsWeightCount() && OnlyBoxScreen.getIsOnly())
        {
            resultBox.setText(calculateWeightAndOnly());
        }
        ////Executes if the "# of Parts" method is being used and only one box was worked on.
        else if (!MainActivity.getIsWeightCount() && OnlyBoxScreen.getIsOnly())
        {
            resultBox.setText(calculateNumberAndOnly());
        }
        //If none of the previous possibilities occur, then the TextView
        //box in the middle of the screen will tell the user that an error occurred.
        else
        {
            resultBox.setText("\"an error occured\"");
        }

        //"Main Menu" button
        //Will return the user to the main menu (MainActivity.class) and will close all other activities.
        Button mainMenu = findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent Constructor: Intent(where you are , where you want to go)
                Intent returnToMainMenu = new Intent(getApplicationContext() , MainActivity.class);
                returnToMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(returnToMainMenu);
            }
        });
    }

    private static String calculateWeightAndNotOnly()
    {
        try
        {
            float startingPartialWeightAdded;
            //The previous screens should have already ensured that StartWeight is not greater than FullWeight
            //If that wasn't caught, startingPartialWeightAdded will default to zero;
            if (StartWeight.getStartWeight() > FullWeight.getFullWeight())
                startingPartialWeightAdded = 0;
            else
                startingPartialWeightAdded = FullWeight.getFullWeight() - StartWeight.getStartWeight();

            float endingPartialWeight = EndWeight.getEndWeight();
            float weightFromPartials = startingPartialWeightAdded + endingPartialWeight;

            //If FullWeight is somehow less than or equal to zero at this point, then it
            //will be treated as "1" in order to avoid dividing by zero or the result being negative.
            float weightOfFullBox = FullWeight.getFullWeight();
            if (weightOfFullBox <= 0)
                weightOfFullBox =1;

            //Converting from float to int results in any decimal values getting cut off.
            //This is fitting because there cannot be partial parts.
            int partsFromPartials = (int) ((weightFromPartials / weightOfFullBox) * PartsInFull.getPartsInFull());

            int partsFromFullBoxes = PartsInFull.getPartsInFull() * BoxesFilled.getBoxesFilled();

            Integer totalParts = partsFromFullBoxes + partsFromPartials;

            return totalParts.toString();
        }
        catch (Exception e)
        {
            return "Error: value could not be calculated";
        }
    }

    private static String calculateWeightAndOnly()
    {
        try
        {
            float weightAdded = EndWeight.getEndWeight() - StartWeight.getStartWeight();

            //If FullWeight is somehow less than or equal to zero at this point, then it
            //will be treated as "1" in order to avoid dividing by zero or the result being negative.
            float weightOfFullBox = FullWeight.getFullWeight();
            if (weightOfFullBox <= 0)
                weightOfFullBox =1;

            //Converting from float to int results in any decimal values getting cut off.
            //This is fitting because there cannot be partial parts.
            Integer partsAdded = (int) ((weightAdded / weightOfFullBox) * PartsInFull.getPartsInFull());

            return partsAdded.toString();
        }
        catch (Exception e)
        {
            return "Error: value could not be calculated";
        }
    }

    private static String calculateNumberAndNotOnly()
    {
        try
        {
            int startingPartialPartsAdded;
            //The previous screens should have already ensured that StartNumber is not greater than PartsInFull
            //If that wasn't caught, startingPartialPartsAdded will default to zero;
            if (StartNumber.getStartNumber() > PartsInFull.getPartsInFull())
                startingPartialPartsAdded = 0;
            else
                startingPartialPartsAdded = PartsInFull.getPartsInFull() - StartNumber.getStartNumber();

            int endingPartialParts = EndNumber.getEndNumber();
            int partsFromPartials = startingPartialPartsAdded + endingPartialParts;

            int partsFromFullBoxes = PartsInFull.getPartsInFull() * BoxesFilled.getBoxesFilled();

            Integer totalParts = partsFromFullBoxes + partsFromPartials;

            return totalParts.toString();
        }
        catch (Exception e)
        {
            return "Error: value could not be calculated";
        }
    }

    private static String calculateNumberAndOnly()
    {
        try
        {
            Integer totalParts = EndNumber.getEndNumber() - StartNumber.getStartNumber();

            return totalParts.toString();
        }
        catch (Exception e)
        {
            return "Error: value could not be calculated";
        }
    }
}
