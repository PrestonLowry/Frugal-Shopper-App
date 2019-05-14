/*
This program was programmed by:
Preston Lowry
811825692
Kcchut's 4060 Class
11 February 2019
*/
package edu.uga.cs.frugalshopper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Instantiate objects that will be modified - Unchanging objects ignored
    EditText poundsA, ouncesA;
    EditText poundsB, ouncesB;
    EditText poundsC, ouncesC;
    TextView priceA, calcPriceA;
    TextView priceB, calcPriceB;
    TextView priceC, calcPriceC;
    Button   calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Find the UI elements and define them for the program
        */
        poundsA = findViewById( R.id.poundsA );
        ouncesA = findViewById( R.id.ouncesA );
        poundsB = findViewById( R.id.poundsB );
        ouncesB = findViewById( R.id.ouncesB );
        poundsC = findViewById( R.id.poundsC );
        ouncesC = findViewById( R.id.ouncesC );
        priceA = findViewById( R.id.priceA );
        priceB = findViewById( R.id.priceB );
        priceC = findViewById( R.id.priceC );
        calculateButton = findViewById( R.id.calculateButton );
        calcPriceA = findViewById( R.id.calcPriceA);
        calcPriceB = findViewById( R.id.calcPriceB);
        calcPriceC = findViewById( R.id.calcPriceC);
        // set the button's listener
        calculateButton.setOnClickListener(new ButtonClickListener());
    }
/*
    This listener does the heavy lifting of the program, and calculates
    each of the three products if they have at least 1 ounce and one cent
    for price. Then, it determines which is the smallest and displays a
    Toast message telling the user which objects are the best deal. It accounts
    for multiple identical objects, and also if all three are the same as well
*/
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //define most everything
            double totalweightA = 0, totalweightB = 0, totalweightC = 0;
            double unitA, unitB, unitC;
            String priceAStr = priceA.getText().toString();
            String priceBStr = priceB.getText().toString();
            String priceCStr = priceC.getText().toString();
            String tempMsg = "";
            try {
                // obtain the values entered by the user
                // converts the pounds to ounces and adds it to the ounces of each.
                totalweightA = (Double.parseDouble(poundsA.getText().toString())*16)+(Double.parseDouble(ouncesA.getText().toString()));
                totalweightB = (Double.parseDouble(poundsB.getText().toString())*16)+(Double.parseDouble(ouncesB.getText().toString()));
                totalweightC = (Double.parseDouble(poundsC.getText().toString())*16)+(Double.parseDouble(ouncesC.getText().toString()));
            }
            //This catches if the user somehow manages to put a negative value in
            //although the user is disallowed from that normally
            catch( NumberFormatException nfe ) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter only positive values, please",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            //Checks to see if there are any products typed in that weigh nothing
            if( totalweightA == 0.0 && totalweightB == 0.0 && totalweightC == 0.0 ) {
                Toast weightToast = Toast.makeText(getApplicationContext(),
                        "You need at least one product for the app to function",
                        Toast.LENGTH_SHORT);
                weightToast.show();
                return;
            }
            //Checks to see if any products are free
            if( Double.parseDouble(priceAStr) <= 0.0 &&  Double.parseDouble(priceBStr) <= 0.0 &&  Double.parseDouble(priceCStr) <= 0.0 ) {
                Toast priceToast = Toast.makeText(getApplicationContext(),
                        "At least one price must be > 0, there are no free lunches",
                        Toast.LENGTH_SHORT);
                priceToast.show();
                return;
            }
            //Performs the equation (price/total weight) and then makes a copy
            //to round up to actual currency for the display. Ignores the original
            //calculations for a more precise answer at higher and higher numbers
            //when comparisons occur later.
            unitA = Double.parseDouble(priceA.getText().toString()) / totalweightA ;
            unitB = Double.parseDouble(priceB.getText().toString()) / totalweightB;
            unitC = Double.parseDouble(priceC.getText().toString()) / totalweightC ;
            Double displayUnitA = (double) Math.round(unitA*100)/100;
            Double displayUnitB = (double) Math.round(unitB*100)/100;
            Double displayUnitC = (double) Math.round(unitC*100)/100;
            /*
            These three if statements are here to prevent any mistakes in
            calculation due to the ultimate deal of buying something for $0.
            This prevents it by resetting the unit price to an absurdly high
            number, so that it automatically loses out on any comparisons
             */
            if(Double.parseDouble(priceA.getText().toString()) == 0)
            {
                unitA = 9999999;
            }
            if(Double.parseDouble(priceB.getText().toString()) == 0)
            {
                unitB = 9999999;
            }
            if(Double.parseDouble(priceC.getText().toString()) == 0)
            {
                unitC = 9999999;
            }
            //changes the text and formats it to $X.XX/oz
            String tempA = "$" + (Double.toString(displayUnitA)) + "/oz";
            String tempB = "$" + (Double.toString(displayUnitB)) + "/oz";
            String tempC = "$" + (Double.toString(displayUnitC)) + "/oz";
            calcPriceA.setText(tempA);
            calcPriceB.setText(tempB);
            calcPriceC.setText(tempC);
            //

            //sets the default smallest to A
            Double smallest = unitA;
            //Checks all the values against A
            tempMsg = "Product(s) A";
            if(smallest > unitB)
            {
                smallest = unitB;
                tempMsg = "Product(s) B";
            }
            //If A = B, concatenate B to the string as well
            else if (smallest == unitB)
            {
                tempMsg += " and B";
            }
            if(smallest > unitC)
            {
                smallest = unitC;
                tempMsg = "Product C";
            }
            //if A or B = C, concatenates C to the string as well
            else if (smallest == unitC)
            {
                tempMsg += " and C"; //appends C to message
            }
            //If all three are equivalent, tell the user.
            if (unitA == unitB && unitB == unitC)
            {
                tempMsg = "All 3 Are Equal";
            }
            //Create the string that the Toast displayer shows
            String tempMsg2 = "Your Best Value Option Is: " + tempMsg;

            //Instantiate and use the Toast object to display the best deal
            Toast smallestToast = Toast.makeText(getApplicationContext(),
                    tempMsg2,
                    Toast.LENGTH_LONG);
            smallestToast.show();

        }
    }
}
