package com.example.aditya.gojek.ui.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.TestComponentRule;
import com.example.aditya.gojek.data.local.DatabaseHelper;
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
import static io.appflate.restmock.utils.RequestMatchers.pathContains;

/**
 * An Instrumentation test for {@link MainActivity}
 * using {@link android.support.test.espresso.Espresso} and {@link okhttp3.mockwebserver.MockWebServer}
 * Created by Aditya on 13-Feb-17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private GoJekService goJekService;
    public final TestComponentRule mComponent =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public TestRule chain = RuleChain.outerRule(mComponent).around(activityTestRule);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(200, "contact/contacts.json");
        goJekService = GoJekServiceFactory.makeGoJekService(RESTMockServer.getUrl());
    }

    @After
    public void tearDown() throws Exception {
        InstrumentationRegistry.getContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Test
    public void shouldShowContacts_whenApiReturnsResults() throws Exception {
        goJekService.getContacts().toObservable();
        activityTestRule.launchActivity(null);
        onView(withId(R.id.recyclerView_contact)).check(matches(isDisplayed()));
    }


}