package com.example.aditya.gojek.ui.detail;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.TestComponentRule;
import com.example.aditya.gojek.data.local.DatabaseHelper;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.data.remote.GoJekService;
import com.example.aditya.gojek.data.remote.GoJekServiceFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * An Instrumentation test for {@link ContactDetailActivity}
 * using {@link android.support.test.espresso.Espresso} and {@link okhttp3.mockwebserver.MockWebServer}
 * Created by Aditya on 13-Feb-17.
 */
@RunWith(AndroidJUnit4.class)
public class ContactDetailActivityTest {
    private GoJekService goJekService;
    private final TestComponentRule mComponent = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    private final ActivityTestRule<ContactDetailActivity> activityTestRule = new ActivityTestRule<>(ContactDetailActivity.class, false, false);

    private static final int RESULT_OK = 200;
    private static final int RESULT_ERROR = 400;

    @Rule public TestRule chain = RuleChain.outerRule(mComponent).around(activityTestRule);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
        goJekService = GoJekServiceFactory.makeGoJekService(RESTMockServer.getUrl());
    }

    @After
    public void tearDown() throws Exception {
        InstrumentationRegistry.getContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }


    @Test
    public void shouldDisplayName_whenLaunchedActivity() throws Exception {
        Intent intent = new Intent(InstrumentationRegistry.getContext(), ContactDetailActivity.class);
        Contact contact = new Contact();
        contact.setFirstName("Aditya");
        contact.setLastName("Ladwa");
        contact.setFavorite(true);
        intent.putExtra("extra_contact", contact);
        activityTestRule.launchActivity(intent);

        onView(withId(R.id.txt_contact_name)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_contact_name)).
                check(matches(withText(contact.getFirstName() + " " + contact.getLastName())));

        onView(withId(R.id.txt_contact_email)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_contact_email)).check(matches(withText("Loading…")));


        onView(withId(R.id.txt_contact_number)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_contact_number)).check(matches(withText("Loading…")));
    }
}