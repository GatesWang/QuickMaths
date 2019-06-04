// Copyright MyScript. All rights reserved.

package com.myscript.iink.getstarted;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.myscript.iink.Configuration;
import com.myscript.iink.ContentPackage;
import com.myscript.iink.ContentPart;
import com.myscript.iink.Editor;
import com.myscript.iink.Engine;
import com.myscript.iink.IEditorListener;
import com.myscript.iink.uireferenceimplementation.EditorView;
import com.myscript.iink.uireferenceimplementation.FontUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
  private static final String TAG = "MainActivity";

  private Engine engine;
  private ContentPackage contentPackage;
  private ContentPart contentPart;
  private EditorView editorView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    ErrorActivity.installHandler(this);

    engine = IInkApplication.getEngine();

    // configure recognition
    Configuration conf = engine.getConfiguration();
    String confDir = "zip://" + getPackageCodePath() + "!/assets/conf";
    conf.setStringArray("configuration-manager.search-path", new String[]{confDir});
    String tempDir = getFilesDir().getPath() + File.separator + "tmp";
    conf.setString("content-package.temp-folder", tempDir);

    setContentView(R.layout.activity_main);

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


    String packageName = "File1.iink";
    File file = new File(getFilesDir(), packageName);
    try
    {
      contentPackage = engine.createPackage(file);
      contentPart = contentPackage.createPart("Text Document"); // Choose type of content (possible values are: "Text Document", "Text", "Diagram", "Math", and "Drawing")
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

    super.onDestroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.activity_main, menu);

    MenuItem convertMenuItem = menu.findItem(R.id.menu_convert);
    convertMenuItem.setEnabled(true);

    return super.onCreateOptionsMenu(menu);
  }

}
