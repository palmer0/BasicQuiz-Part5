package es.ulpgc.eite.da.basicquizlab;


import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LandscapeQuestionTest {

  @Rule
  public ActivityTestRule<QuestionActivity> mActivityTestRule =
      new ActivityTestRule<>(QuestionActivity.class);

  @Test
  public void lanscapeQuestionTest() {

    // GIVEN

    ViewInteraction textView17 = onView(
        allOf(withId(R.id.questionText), withText("Question #1: True"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                0),
            isDisplayed()));
    textView17.check(matches(withText("Question #1: True")));

    ViewInteraction textView18 = onView(
        allOf(withId(R.id.replyText), withText("???"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                2),
            isDisplayed()));
    textView18.check(matches(withText("???")));


    // WHEN

    ViewInteraction appCompatButton = onView(
        allOf(withId(R.id.cheatButton), withText("Cheat"),
            childAtPosition(
                childAtPosition(
                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                    3),
                2),
            isDisplayed()));
    appCompatButton.perform(click());

    // THEN & GIVEN

    ViewInteraction textView = onView(
        allOf(withId(R.id.warningText), withText("Are you sure?"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                0),
            isDisplayed()));
    textView.check(matches(withText("Are you sure?")));

    ViewInteraction textView2 = onView(
        allOf(withId(R.id.answerText), withText("???"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                2),
            isDisplayed()));
    textView2.check(matches(withText("???")));

    // ----------------------

    // WHEN

    ViewInteraction appCompatButton2 = onView(
        allOf(withId(R.id.yesButton), withText("Yes"),
            childAtPosition(
                allOf(withId(R.id.linearLayout),
                    childAtPosition(
                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                        1)),
                0),
            isDisplayed()));

    appCompatButton2.perform(click());

    // THEN & GIVEN

    ViewInteraction textView3 = onView(
        allOf(withId(R.id.warningText), withText("Are you sure?"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                0),
            isDisplayed()));
    textView3.check(matches(withText("Are you sure?")));

    ViewInteraction textView4 = onView(
        allOf(withId(R.id.answerText), withText("True"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                2),
            isDisplayed()));
    textView4.check(matches(withText("True")));

    // ----------------------

    // WHEN

    mActivityTestRule.getActivity()
        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    // THEN & GIVEN

    ViewInteraction textView5 = onView(
        allOf(withId(R.id.warningText), withText("Are you sure?"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                0),
            isDisplayed()));
    textView5.check(matches(withText("Are you sure?")));

    ViewInteraction textView6 = onView(
        allOf(withId(R.id.answerText), withText("True"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                2),
            isDisplayed()));
    textView6.check(matches(withText("True")));

    // ----------------------

    // WHEN

    pressBack();


    // THEN & GIVEN

    ViewInteraction textView7 = onView(
        allOf(withId(R.id.questionText), withText("Question #2: False"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                0),
            isDisplayed()));
    textView7.check(matches(withText("Question #2: False")));

    ViewInteraction textView8 = onView(
        allOf(withId(R.id.replyText), withText("???"),
            childAtPosition(
                childAtPosition(
                    withId(android.R.id.content),
                    0),
                2),
            isDisplayed()));
    textView8.check(matches(withText("???")));
  }

  private static Matcher<View> childAtPosition(
      final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
            && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}
