package com.example.bryan_caesar.goodparts2;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class Utils
{
    //Variable that controls how long the temporary error messages (Toasts) are displayed for.
    //Used by the "showError" method in this class;
    //This variable affects all Toasts displayed in this program.
    //The actual display time will be slightly shorter than this variable (by a matter of milliseconds).
    private static double errorMessageDisplayDuration = 5;

    //Attempts to return the number (of type float) from the passed in EditText box
    //Returns -1 if what is in the box cannot be converted into a float, or if the value is less than zero
    //Is passed in an object of type "EditText" and names it "editText"
    public static float getValue(EditText editText)
    {
        try
        {
            float num = Float.parseFloat(editText.getText().toString());
            if (num < 0)
                throw new IllegalArgumentException("Number must be positive.");
            else
                return num;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    //Attempts to return the number (of type int) from the passed in EditText box
    //Returns -1 if what is in the box cannot be converted into an int, or if the value is less than zero
    //Is passed in an object of type "EditText" and names it "editText"
    public static int getValueInt(EditText editText)
    {
        try
        {
            int num = Integer.parseInt(editText.getText().toString());
            if (num < 0)
                throw new IllegalArgumentException("Number must be positive.");
            else
                return num;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to a float or if what was entered is less than zero.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be a number.
    //If the input can be converted to a float but is negative, the message will only remind the
    //user that the input cannot be negative.
    public static void handleInvalidInput(EditText editText , final Context context)
    {
        String message = "";

        try
        {
            float test = Float.parseFloat(editText.getText().toString());
            if (test < 0)
            {
                message = "Cannot be negative";
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to a float.
                //3.) The resulting float is less than zero.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from a EditText box into a float, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be a number.\nExamples: 4 or 4.2";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }

    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to a float or if what was entered is less than the specified minimum value.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be a number.
    //If the input can be converted to a float but is less than the specified minimum, the message will only remind the
    //user that the input cannot be less than the minimum.
    public static void handleInvalidInput(EditText editText , final float min , final Context context)
    {
        String message = "";

        try
        {
            float test = Float.parseFloat(editText.getText().toString());
            if (test < min)
            {
                message = "Must be at least " +min;
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to a float.
                //3.) The resulting float is less than the specified minimum.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from a EditText box into a float, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be a number.\nExamples: 4 or 4.2";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }

    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to a float or if what was entered is less than the specified minimum value.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be a number.
    //If the input can be converted to a float but is less than the specified minimum, the message will only remind the
    //user that the input cannot be less than the minimum.
    //The minimum will be shown based on the name of the value, not the value itself.
    //Example: "Cannot be less than starting weight" instead of "Cannot be less than 5".
    public static void handleInvalidInput(EditText editText , final float min , final String nameOfMinimumValue , final Context context)
    {
        String message = "";

        try
        {
            float test = Float.parseFloat(editText.getText().toString());
            if (test < min)
            {
                message = "Cannot be less than " + nameOfMinimumValue;
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to a float.
                //3.) The resulting float is less than zero.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from a EditText box into a float, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be a number.\nExamples: 4 or 4.2";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }


    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to an int or if what was entered is less than zero.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be an integer.
    //If the input can be converted to an integer but is negative, the message will only remind the
    //user that the input cannot be negative.
    public static void handleInvalidInputInt(EditText editText , final Context context)
    {
        String message = "";

        try
        {
            int test = Integer.parseInt(editText.getText().toString());
            if (test < 0)
            {
                message = "Cannot be negative";
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to an integer.
                //3.) The resulting int is less than zero.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from a EditText box into an int, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be an integer (whole number).\nExamples: 1, 2, 3, 4";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }

    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to an int or if what was entered is less than the specified minimum value.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be a number.
    //If the input can be converted to an int but is less than the specified minimum, the message will only remind the
    //user that the input cannot be less than the minimum.
    public static void handleInvalidInputInt(EditText editText , final int min , final Context context)
    {
        String message = "";

        try
        {
            int test = Integer.parseInt(editText.getText().toString());
            if (test < min)
            {
                message = "Must be at least " + min;
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to an int.
                //3.) The resulting int is less than the specified minimum.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from an EditText box into an int, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be an integer (whole number).\nExamples: 1, 2, 3, 4";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }

    //This method should only be used to handle occurrences where the input from an EditText
    //box could not be successfully converted to an int or if what was entered is less than the specified minimum value.
    //If this method is called and neither condition is met, the program will display an error
    //on the screen, including the name of the class where this method was called from.
    //This method will make the EditText box empty and will display a message reminding
    //the user that input cannot be empty and must be an integer.
    //If the input can be converted to an int but is less than the specified minimum, the message will only remind the
    //user that the input cannot be less than the minimum.
    //The minimum will be shown based on the name of the value, not the value itself.
    //Example: "Cannot be less than starting number" instead of "Cannot be less than 5".
    public static void handleInvalidInputInt(EditText editText , final int min , final String nameOfMinimumValue , final Context context)
    {
        String message = "";

        try
        {
            int test = Integer.parseInt(editText.getText().toString());
            if (test < min)
            {
                message = "Cannot be less than " + nameOfMinimumValue;
            }
            else
            {
                //In order to enter this block of code, the following parameters must be met:
                //1.) This method was called (see comments above method header).
                //2.) The contents of the EditText box can successfully be converted to an int.
                //3.) The resulting int is less than zero.
                //If this method was called as part of a catch block for methods trying to convert
                //the data from a EditText box into an int, then this block of code should never be reached.
                //If this code does execute, then the program will show the name of the .class file that
                //corresponds with the activity that called this method.
                message = "The app is showing an unknown error. Error Occurred in: " + context.getClass().getSimpleName();
            }
        }
        catch (Exception e)
        {
            message = "Input cannot be empty and must be an integer (whole number).\nExamples: 1, 2, 3, 4";
        }
        finally
        {
            //The "Toast" object is used for displaying temporary messages that will automatically disappear after a set time.
            Toast toast = Toast.makeText(context , message , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0 , 0);
            showError(toast);
            editText.setText("");
        }
    }

    //This method is used to display "Toasts" (temporary messages).
    //The purpose of this method is to allow the Toast to be displayed for longer than what "Toast.LENGTH_LONG" would normally allow.
    //The display time will still have a minimum of 2 seconds.
    //The actual display time may be slightly shorter than this variable (by a matter of milliseconds), in order to insure that
    //the message appears to be a single Toast.
    public static void showError(final Toast toast)
    {
        Thread t = new Thread()
        {
            public void run()
            {
                int count = 0;
                try
                {
                    do
                    {
                        toast.show();

                        //The second Toast will start 1.5 seconds after the current one starts,
                        //in order to ensure that the message appears to be continuous.
                        //Results in the total message display time being 5 seconds.
                        sleep(1500);
                        count++;
                    } while (count <= 1);
                }
                catch (Exception e)
                {
                    toast.cancel();
                }
            }
        };
        t.start();
    }

}