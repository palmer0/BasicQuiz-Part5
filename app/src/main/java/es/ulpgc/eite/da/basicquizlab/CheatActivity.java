package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.CheatActivity";


  public final static String EXTRA_ANSWER = "EXTRA_ANSWER";
  public final static String EXTRA_CHEATED = "EXTRA_CHEATED";
  public final static String KEY_ANSWER = "KEY_ANSWER";
  public final static String KEY_CHEATED = "KEY_CHEATED";

  private Button noButton, yesButton;
  private TextView warningText, answerText;

  private int currentAnswer;
  private boolean answerCheated;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    initLayoutData();

    linkLayoutComponents();
    initLayoutContent();

    if(savedInstanceState != null) {
      currentAnswer = savedInstanceState.getInt(KEY_ANSWER);
      answerCheated = savedInstanceState.getBoolean(KEY_CHEATED);

      if(answerCheated) {
        updateLayoutContent();
      }
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt(KEY_ANSWER, currentAnswer);
    outState.putBoolean(KEY_CHEATED, answerCheated);
  }

  private void initLayoutData() {
    currentAnswer = getIntent().getExtras().getInt(EXTRA_ANSWER);
  }

  private void linkLayoutComponents() {
    noButton = findViewById(R.id.noButton);
    yesButton = findViewById(R.id.yesButton);

    warningText = findViewById(R.id.warningText);
    answerText = findViewById(R.id.answerText);
  }

  private void initLayoutContent() {
    noButton.setText(R.string.no_button_text);
    yesButton.setText(R.string.yes_button_text);

    warningText.setText(R.string.warning_text);
    answerText.setText(R.string.empty_text);
  }

  private void returnCheatedStatus() {
    Log.d(TAG, "returnCheatedStatus()");
    Log.d(TAG, "answerCheated: " + answerCheated);

    Intent intent = new Intent();
    intent.putExtra(EXTRA_CHEATED, answerCheated);
    setResult(RESULT_OK, intent);

    finish();

  }

  @Override
  public void onBackPressed() {
    Log.d(TAG, "onBackPressed()");

    returnCheatedStatus();
  }

  public void onButtonClick(View view) {

    switch (view.getId()) {
      case R.id.noButton:
        noButtonClicked();
        break;
      case R.id.yesButton:
        yesButtonClicked();
    }

  }

  private void yesButtonClicked() {
    answerCheated = true;
    updateLayoutContent();
  }

  private void updateLayoutContent() {

    if(currentAnswer == 0) {
      answerText.setText(R.string.false_text);
    } else {
      answerText.setText(R.string.true_text);

    }
  }

  private void noButtonClicked() {
    returnCheatedStatus();
  }


}
