package mushirih.thoughtleadership2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

/**
 * Created by p-tah on 25/01/2016.
 */
public class Splash extends Activity {

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
           Intent newIntent=new Intent(getBaseContext(), Home.class);
            startActivity(newIntent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);/*
        setContentView(R.layout.splash);
        ImageView splash= (ImageView) findViewById(R.id.imagesplash);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
        splash.startAnimation(animation);
        handler.postDelayed(runnable,3000);*/

         VideoView videoHolder = (VideoView) findViewById(R.id.spl);

        try{
  Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
          + R.raw.splashh);

  videoHolder.setVideoURI(video);
   videoHolder.requestFocus();
            videoHolder.setZOrderOnTop(true);
  videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

      public void onCompletion(MediaPlayer mp) {
          Intent newIntent = new Intent(getBaseContext(), Home.class);

          startActivity(newIntent);
      }

  });

  videoHolder.start();
 } catch(Exception ex) {
     Intent newIntent=new Intent(getBaseContext(), Home.class);
     startActivity(newIntent);
 }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        finish();
    }
}
