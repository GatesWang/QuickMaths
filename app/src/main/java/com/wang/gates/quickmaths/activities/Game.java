// Copyright MyScript. All rights reserved.

package com.wang.gates.quickmaths.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.myscript.iink.*;
import com.myscript.iink.uireferenceimplementation.EditorView;
import com.myscript.iink.uireferenceimplementation.FontUtils;
import com.myscript.iink.uireferenceimplementation.InputController;
import com.wang.gates.quickmaths.R;

import com.wang.gates.quickmaths.classes.*;
import com.wang.gates.quickmaths.custom_views.DrawViewProblem;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Game extends AppCompatActivity
{
  private static final String TAG = "MainActivity";

  private Engine engine;
  private ContentPackage contentPackage;
  private ContentPart contentPart;
  private EditorView editorView;

  private ProblemGenerator generator = ProblemGenerator.Companion.getInstance();
  private Settings settings = Settings.Companion.getInstance();
  private Problem problem = generator.getProblem();
  private State state = null;

  private ProgressBar progressBar = null;
  private DrawViewProblem drawViewProblem = null;
  private FloatingActionButton clearCanvas = null;
  private Button submitAnswer = null;

  private String input = null;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    setContentView(R.layout.game);
    //find views
    progressBar = findViewById(R.id.progressBar);
    drawViewProblem = findViewById(R.id.draw_view_problem);
    clearCanvas = findViewById(R.id.clear_canvas);
    submitAnswer = findViewById(R.id.submit_answer);

    super.onCreate(savedInstanceState);
    engine = IInkApplication.getEngine();

    // configure recognition
    Configuration conf = engine.getConfiguration();
    String confDir = "zip://" + getPackageCodePath() + "!/assets/conf";
    conf.setStringArray("configuration-manager.search-path", new String[]{confDir});
    String tempDir = getFilesDir().getPath() + File.separator + "tmp";
    conf.setString("content-package.temp-folder", tempDir);

    editorView = findViewById(R.id.editor_view);

    // load fonts
    AssetManager assetManager = getApplicationContext().getAssets();
    Map<String, Typeface> typefaceMap = FontUtils.loadFontsFromAssets(assetManager);
    editorView.setTypefaces(typefaceMap);

    editorView.setEngine(engine);

    final Editor editor = editorView.getEditor();
    editor.addListener(new IEditorListener()
    {
      @Override
      public void partChanging(Editor editor, ContentPart oldPart, ContentPart newPart)
      {
        // no-op
      }

      @Override
      public void partChanged(Editor editor)
      {
        invalidateOptionsMenu();
      }

      @Override
      public void contentChanged(Editor editor, String[] blockIds)
      {
        invalidateOptionsMenu();
      }

      @Override
      public void onError(Editor editor, String blockId, String message)
      {
        Log.e(TAG, "Failed to edit block \"" + blockId + "\"" + message);
      }
    });

    setInputMode(InputController.INPUT_MODE_FORCE_PEN); // If using an active pen, put INPUT_MODE_AUTO here

    String packageName = "File1.iink";
    File file = new File(getFilesDir(), packageName);
    try
    {
      contentPackage = engine.createPackage(file);
      contentPart = contentPackage.createPart("Math"); // Choose type of content (possible values are: "Text Document", "Text", "Diagram", "Math", and "Drawing")
    }
    catch (IOException e)
    {
      Log.e(TAG, "Failed to open package \"" + packageName + "\"", e);
    }
    catch (IllegalArgumentException e)
    {
      Log.e(TAG, "Failed to open package \"" + packageName + "\"", e);
    }

    setTitle("Type: " + contentPart.getType());

    // wait for view size initialization before setting part
    editorView.post(new Runnable()
    {
      @Override
      public void run()
      {
        editorView.getRenderer().setViewOffset(0, 0);
        editorView.getRenderer().setViewScale(1);
        editorView.setVisibility(View.VISIBLE);
        editor.setPart(contentPart);
      }
    });

    setProgressBarColor(progressBar);
    drawNewProblem();
    setListeners();

    state = new State(this);
    state.createTimer();
    state.startTimer();
  }

  private void setListeners(){
    clearCanvas.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawViewProblem.clearPath();
        editorView.getEditor().clear();
      }
    });
    submitAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        submitAnswer();
      }
    });
  }

  private void drawNewProblem(){
    problem = generator.getProblem();
    drawViewProblem.setProblem(problem);
    drawViewProblem.clearPath();
    editorView.getEditor().clear();
  }

  private void submitAnswer(){
    convert();

    if(!inputIsValid()) {
      Toast.makeText(Game.this, "invalid input", Toast.LENGTH_SHORT).show();
      editorView.getEditor().clear();
      drawViewProblem.clearPath();
      return;
    }

    if(inputIsCorrect()){
      state.addToCount();
      drawNewProblem();
      ifBombModeLowerTime();
    }
    else{
      Toast.makeText(Game.this, "incorrect", Toast.LENGTH_SHORT).show();
      editorView.getEditor().clear();
      drawViewProblem.clearPath();
      ifBombModeFinishGame();
    }
  }

  private void ifBombModeLowerTime(){
    if(settings.getMode()==Settings.BOMB_MODE) {
      state.stopTimer();
      state.recalculateTime();
      state.createTimer();
      state.startTimer();
    }
  }

  private boolean inputIsValid(){
    try {
      Integer inputInteger = Integer.parseInt(input);
    } catch (NumberFormatException | NullPointerException nfe) {
      return false;
    }
    return true;
  }

  private boolean inputIsCorrect(){
    Integer inputInteger = Integer.parseInt(input);
    return (inputInteger == problem.getProblemAnswer());
}

  private  void setProgressBarColor(ProgressBar progress){
    Integer color = ContextCompat.getColor(progress.getContext(), R.color.colorPrimaryDark);
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    progress.setProgressTintList(ColorStateList.valueOf(color));
  } else{
    LayerDrawable layerDrawable = (LayerDrawable) progress.getProgressDrawable();
    Drawable progressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
    progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
  }
}

  private void ifBombModeFinishGame(){
    if(settings.getMode()==Settings.BOMB_MODE) {
      finishGame();
    }
  }

  public void finishGame(){
    Intent intent = new Intent(Game.this, GameScore.class);
    intent.putExtra("score", state.getScore());
    startActivity(intent);
    finish();
  }

  private void convert(){
    Editor editor = editorView.getEditor();
    editor.waitForIdle();
    ConversionState[] supportedStates = editor.getSupportedTargetConversionStates(null);
    if (supportedStates.length > 0) {
      editor.convert(null, supportedStates[0]);
      try {
        String jiixString = editor.export_(null, MimeType.JIIX);
        int start = 8 + jiixString.indexOf("value");
        int end = start;
        while(jiixString.charAt(end)!=','){
          end++;
        }
        input = jiixString.substring(start, end);
      }
      catch(Exception e){

      }
    }
  }

  private void setInputMode(int inputMode)
  {
    editorView.setInputMode(inputMode);
  }

  @Override
  protected void onDestroy()
  {
    editorView.setOnTouchListener(null);
    editorView.close();

    if (contentPart != null)
    {
      contentPart.close();
      contentPart = null;
    }
    if (contentPackage != null)
    {
      contentPackage.close();
      contentPackage = null;
    }


    // IInkApplication has the ownership, do not close here
    engine = null;
    state.stopTimer();
    super.onDestroy();
  }
}