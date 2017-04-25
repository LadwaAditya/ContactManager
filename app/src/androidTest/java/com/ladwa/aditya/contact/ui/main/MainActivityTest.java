package com.ladwa.aditya.contact.ui.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ladwa.aditya.contact.R;
import com.ladwa.aditya.contact.TestComponentRule;
import com.ladwa.aditya.contact.data.local.DatabaseHelper;
import com.ladwa.aditya.contact.data.remote.GoJekService;
import com.ladwa.aditya.contact.data.remote.GoJekServiceFactory;

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
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ladwa.aditya.contact.TestApplication.PATH_ASSETS_CONTACT;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;

/**
 * An Instrumentation test for {@link MainActivity}
 * using {@link android.support.test.espresso.Espresso} and {@link okhttp3.mockwebserver.MockWebServer}
 * Created by Aditya on 13-Feb-17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private GoJekService goJekService;
    private final TestComponentRule mComponent = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    private final ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    private static final int RESULT_OK = 200;
    private static final int RESULT_ERROR = 400;
    private static final String PATH_CONTACTS = "/contacts.json";
    private static final String PATH_CONTACTS_ZERO = "/contactszero.json";


    @Rule public TestRule chain = RuleChain.outerRule(mComponent).around(activityTestRule);

    @Before public void setUp() throws Exception {
        RESTMockServer.reset();
        goJekService = GoJekServiceFactory.makeGoJekService(RESTMockServer.getUrl());
    }

    @After
    public void tearDown() throws Exception {
        InstrumentationRegistry.getContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Test public void shouldShowContacts_whenApiReturnsResults() throws Exception {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(RESULT_OK, PATH_ASSETS_CONTACT + PATH_CONTACTS);
        goJekService.getContacts().toObservable();

        activityTestRule.launchActivity(null);

        onView(withId(R.id.recyclerView_contact)).check(matches(isDisplayed()));
    }

    @Test public void shouldShowCorrectContact_whenApiReturnsResults() throws Exception {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(RESULT_OK, PATH_ASSETS_CONTACT + PATH_CONTACTS);
        goJekService.getContacts().toObservable();

        activityTestRule.launchActivity(null);

        onView(withId(R.id.recyclerView_contact)).check(matches(isDisplayed()));
        onView(withText("Aditya Ladwa")).check(matches(isDisplayed()));
    }

    @Test public void shouldDisplayErrorMessage_whenApiReturnsZeroContacts() throws Exception {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(RESULT_OK, PATH_ASSETS_CONTACT + PATH_CONTACTS_ZERO);
        goJekService.getContacts().toObservable();

        activityTestRule.launchActivity(null);

        onView(withId(R.id.txt_no_contacts)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test public void shouldDisplayErrorMessage_whenApiReturnsError() throws Exception {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(RESULT_ERROR);
        goJekService.getContacts().toObservable();

        activityTestRule.launchActivity(null);

        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }
}