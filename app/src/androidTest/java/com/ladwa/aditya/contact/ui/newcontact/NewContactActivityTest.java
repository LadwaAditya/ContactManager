package com.ladwa.aditya.contact.ui.newcontact;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ladwa.aditya.contact.R;
import com.ladwa.aditya.contact.TestComponentRule;
import com.ladwa.aditya.contact.data.local.DatabaseHelper;
import com.ladwa.aditya.contact.data.remote.ContactService;
import com.ladwa.aditya.contact.data.remote.ContactServiceFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 *
 * Created by Aditya on 14-Feb-17.
 */
@RunWith(AndroidJUnit4.class)
public class NewContactActivityTest {
    private ContactService contactService;
    private final TestComponentRule mComponent = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    private final ActivityTestRule<NewContactActivity> activityTestRule = new ActivityTestRule<>(NewContactActivity.class, false, false);

    private static final int RESULT_OK = 200;
    private static final int RESULT_ERROR = 400;

    @Rule public TestRule chain = RuleChain.outerRule(mComponent).around(activityTestRule);


    @Before public void setUp() throws Exception {
        RESTMockServer.reset();
        contactService = ContactServiceFactory.makeGoJekService(RESTMockServer.getUrl());
    }

    @After public void tearDown() throws Exception {
        InstrumentationRegistry.getContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);

    }

    @Test public void shouldShowSnackBar_whenAllFieldsAreEmpty() throws Exception {

        activityTestRule.launchActivity(null);

        onView(withId(R.id.btn_save)).perform(click());
        checkSnackBarVisiblity(R.string.email_is_invalid);
    }

    @Test public void shouldShowSnackBar_whenPhoneNumberIsEmpty() throws Exception {
        activityTestRule.launchActivity(null);

        typeTextToView(R.id.txt_email, "ladwa.aditya@gmail.com");

        onView(withId(R.id.btn_save)).perform(click());
        checkSnackBarVisiblity(R.string.phone_number_invalid);

    }


    @Test public void shouldShowSnackBar_whenNameIsEmpty() throws Exception {

        activityTestRule.launchActivity(null);

        typeTextToView(R.id.txt_email, "ladwa.aditya@gmail.com");
        typeTextToView(R.id.txt_mobile_number, "+917411438334");
        typeTextToView(R.id.txt_last_name, "Ladwa");

        onView(withId(R.id.btn_save)).perform(click());
        checkSnackBarVisiblity(R.string.first_name_invalid);
    }

    @Test public void shouldShowSnackBar_whenLastNameIsEmpty() throws Exception {
        activityTestRule.launchActivity(null);

        typeTextToView(R.id.txt_first_name, "Aditya");
        typeTextToView(R.id.txt_email, "ladwa.aditya@gmail.com");
        typeTextToView(R.id.txt_mobile_number, "+917411438334");


        onView(withId(R.id.btn_save)).perform(click());
        checkSnackBarVisiblity(R.string.invalid_last_name);

    }


    private void typeTextToView(@IdRes int viewId, String textToType) {
        onView(withId(viewId)).perform(typeText(textToType));
        closeSoftKeyboard();
    }

    private void checkSnackBarVisiblity(@StringRes int stringRes) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(stringRes)))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }
}