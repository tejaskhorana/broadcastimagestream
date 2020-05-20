package com.example.applicationone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** This Activity sends the broadcast. */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Send a broadcast with a provided text.
    Button sendBroadcastButton = (Button) findViewById(R.id.sendBroadcast);
    sendBroadcastButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        for(int i = 0 ; i < 200 ; i++) {
          int subIteration = i % 3;
          int imageId = subIteration == 0 ? R.drawable.moon_icon : subIteration == 1 ? R.drawable.sun_icon : R.drawable.cloud_icon;
          createAndSendIntent(imageId);

        }
      }
    });
  }

  private void createAndSendIntent(int imageId) {
    Intent intent = new Intent();
    intent.setAction("com.example.applicationone.SCANNED_IMAGE");

    // Ensures this reaches an app even if it is not running.
    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
    intent.putExtra("scan_img", convertBitmapToByteArray(bitmap));

    getApplicationContext().sendBroadcast(intent);
    Log.w("Application One", "Broadcast sent..");
  }

  private byte[] convertBitmapToByteArray(Bitmap bitmap) {
    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
      return baos.toByteArray();
    } finally {
      if (baos != null) {
        try {
          baos.close();
        } catch (IOException e) {
          Log.e("ApplicationOne", "ByteArrayOutputStream was not closed");
        }
      }
    }
  }
}