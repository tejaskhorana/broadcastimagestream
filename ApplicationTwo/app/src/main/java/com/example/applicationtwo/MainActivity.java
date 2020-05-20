package com.example.applicationtwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/** This Activity receives broadcasted images. */
public class MainActivity extends AppCompatActivity {
  private MyBroadcastReceiver myReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Register receiver to receive broadcast from external app
    IntentFilter filter = new IntentFilter("com.example.applicationone.SCANNED_IMAGE");
    myReceiver = new MyBroadcastReceiver();
    registerReceiver(myReceiver, filter);
  }

  public class MyBroadcastReceiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.w("ApplicationTwo", "Broadcast Received.");
      byte[] byteArray = intent.getByteArrayExtra("scan_img");
      Bitmap myImage = convertCompressedByteArrayToBitmap(byteArray);
      ImageView imageView = findViewById(R.id.image_view);
      imageView.setImageBitmap(myImage); // Display received image.
      Log.w("ApplicationTwo", "Image Displayed.");
    }
  }

  private Bitmap convertCompressedByteArrayToBitmap(byte[] src) {
    return BitmapFactory.decodeByteArray(src, 0, src.length);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(myReceiver != null)
      unregisterReceiver(myReceiver);
  }
}
