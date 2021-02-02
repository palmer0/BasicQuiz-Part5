package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.QuestionActivity";

  public final static String KEY_INDEX = "KEY_INDEX";
  public final static String KEY_REPLY = "KEY_REPLY";
  public final static String KEY_ENABLED = "KEY_ENABLED";
  public static final int CHEAT_REQUEST = 1;

  private Button falseButton, trueButton,cheatButton, nextButton;
  private TextView questionText, replyText;

  private String[] questionArray;
  private int questionIndex=0;
  private int[] replyArray;
  private boolean nextButtonEnabled;
  private String currentReply;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    getSupportActionBar().setTitle(R.string.question_title);

    initLayoutData();

    linkLayoutComponents();
    initLayoutContent();

    resetReplyContent();

    if(savedInstanceState != null) {
      questionIndex=savedInstanceState.getInt(KEY_INDEX);
      currentReply =savedInstanceState.getString(KEY_REPLY);
      nextButtonEnabled=savedInstanceState.getBoolean(KEY_ENABLED);

    }

    updateLayoutContent();

  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume()");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy()");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt(KEY_INDEX, questionIndex);
    outState.putString(KEY_REPLY, currentReply);
    outState.putBoolean(KEY_ENABLED, nextButtonEnabled);
  }

  private void initLayoutData() {
    questionArray=getResources().getStringArray(R.array.question_array);
    replyArray=getResources().getIntArray(R.array.reply_array);
  }


  private void linkLayoutComponents() {
    falseButton = findViewById(R.id.falseButton);
    trueButton = findViewById(R.id.trueButton);
    cheatButton = findViewById(R.id.cheatButton);
    nextButton = findViewById(R.id.nextButton);

    questionText = findViewById(R.id.questionText);
    replyText = findViewById(R.id.replyText);
  }

  private void initLayoutContent() {
    falseButton.setText(R.string.false_button_text);
    trueButton.setText(R.string.true_button_text);
    nextButton.setText(R.string.next_button_text);
    cheatButton.setText(R.string.cheat_button_text);
  }

  private void resetReplyContent() {
    currentReply = getString(R.string.empty_text);
  }

  private void updateLayoutContent() {
    questionText.setText(questionArray[questionIndex]);
    replyText.setText(currentReply);

    nextButton.setEnabled(nextButtonEnabled);
    cheatButton.setEnabled(!nextButtonEnabled);
    falseButton.setEnabled(!nextButtonEnabled);
    trueButton.setEnabled(!nextButtonEnabled);
  }

  public void onButtonClick(View view) {

    switch (view.getId()) {
      case R.id.falseButton:
      case R.id.trueButton:
        buttonClicked(view.getId());
        break;
      case R.id.nextButton:
        nextButtonClicked();
        break;
      case R.id.cheatButton:
        cheatButtonClicked();
    }

  }

  private void buttonClicked(int id) {

    switch (id) {
      case R.id.falseButton:
        falseButtonClicked();
        break;
      case R.id.trueButton:
        trueButtonClicked();
    }

    nextButtonEnabled = true;
    updateLayoutContent();

  }


  private void trueButtonClicked() {

    if(replyArray[questionIndex] == 1) {
      currentReply=getString(R.string.correct_text);
    } else {
      currentReply=getString(R.string.incorrect_text);
    }


  }

  private void falseButtonClicked() {

    if(replyArray[questionIndex] == 0) {
      currentReply=getString(R.string.correct_text);
    } else {
      currentReply=getString(R.string.incorrect_text);
    }

  }

  private void cheatButtonClicked() {

    Intent intent = new Intent(this, CheatActivity.class);
    intent.putExtra(CheatActivity.EXTRA_ANSWER, replyArray[questionIndex]);
    startActivityForResult(intent, CHEAT_REQUEST);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    Log.d(TAG, "onActivityResult()");

    if (requestCode == CHEAT_REQUEST) {
      if (resultCode == RESULT_OK) {

        boolean answerCheated =
            data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);

        Log.d(TAG, "answerCheated: " + answerCheated);

        if(answerCheated) {
          nextButtonEnabled = true;
          nextButtonClicked();
        }
      }
    }
  }

  private void nextButtonClicked() {
    Log.d(TAG, "nextButtonClicked()");

    nextButtonEnabled = false;
    questionIndex++;

    // si queremos que el quiz acabe al llegar
    // a la ultima pregunta debemos comentar esta linea
    checkIndexData();

    if(questionIndex < questionArray.length) {
      resetReplyContent();
      updateLayoutContent();
    }

  }

  private void checkIndexData() {

    // hacemos que si llegamos al final del quiz
    // volvamos a empezarlo nuevamente
    if(questionIndex == questionArray.length) {
      questionIndex=0;
    }

  }

}
