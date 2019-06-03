// Copyright MyScript. All rights reserved.

package com.wang.gates.quickmaths.classes;

import android.app.Application;
import com.myscript.iink.Engine;
import com.wang.gates.quickmaths.certificate.MyCertificate;

public class IInkApplication extends Application
{
  private static Engine engine;

  public static synchronized Engine getEngine()
  {
    if (engine == null)
    {
      engine = Engine.create(MyCertificate.getBytes());
    }

    return engine;
  }

}
