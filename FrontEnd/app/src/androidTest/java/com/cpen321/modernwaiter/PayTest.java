package com.cpen321.modernwaiter;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.android.volley.toolbox.StringRequest;
import com.cpen321.modernwaiter.customer.application.ApiUtil;
import com.cpen321.modernwaiter.customer.application.CustomerActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.android.volley.Request.Method.GET;

public class PayTest {
    @Before
    public void changeUserAndTableId() throws InterruptedException {
        ApiUtil.notificationEnabled = false;

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CustomerActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("userId", 2);
        bundle.putString("restaurantId", ApiUtil.RESTAURANT_ID);
        bundle.putString("tableId", "2");

        intent.putExtras(bundle);

        ActivityScenario.launch(intent);
        Thread.sleep(1000);
    }

    /**
     * Check if the server is running
     */
    @Test public void checkServerConnection(){
        StringRequest stringRequest = new StringRequest(GET, ApiUtil.health, response -> {
            if(response == null) Assert.fail("Received null response from backend health checkup");
            else Assert.assertTrue( true);
        }, error -> {
            Assert.fail("Could not connect to the server" + error.toString());
        });
    }


    /**
     * Pay for all
     */
    /**
     * view bill
     * initiate payment
     * pay for all
     * input details
     * pay
     * check if successful
     */
    @Test
    public void payForAll() throws InterruptedException {

        onView(withId(R.id.menu_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //add item to cart
        onView(withId(R.id.incrementButton))
                .perform(click());

        //now go back to menu
        onView(withId(R.id.exitButton))
                .perform(click());
        //click view cart
        onView(withId(R.id.viewCartButton))
                .perform(click());
        //click on checkout
        onView(withId(R.id.checkoutButton))
                .perform(click());

        //click on view bill
        onView(withId(R.id.startBillButton))
                .perform(click());

        //check that its displaying the bill
        onView(withId(R.id.fragment_bill))
                .check(matches(isDisplayed()));


        /////////paying the bill///////////

        //initiate payment
        onView(withId(R.id.startPaymentButton))
                .perform(click());

        Thread.sleep(600);
        //check that on payment options page
        onView(withId(R.id.barcode_button))
                .check(matches(isDisplayed()));

        //click on pay_for_all
        onView(withId(R.id.barcode_button))
                .perform(click());

        Thread.sleep(100);


        //input payment details
        String creditCardNumber = "4242" + "4242" + "4242" + "4242";
        String date = "0522";
        String cv = "012";
        String postal = "V3Z8C7";
        //check that the widget is displayed
        onView(withResourceName("card_number_edit_text"))
                .check(matches(isDisplayed()));

        onView(withResourceName("card_number_edit_text"))
                .perform(typeText(creditCardNumber+date+cv+postal), closeSoftKeyboard());
        //press pay
        onView(withId(R.id.payButton))
                .perform(click());

        Thread.sleep(2000);

        //check that payment was successful
        onView(withId(R.id.textView2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.textView2))
                .check(matches(withText(R.string.thank_you_post_payment)));
    }
}
