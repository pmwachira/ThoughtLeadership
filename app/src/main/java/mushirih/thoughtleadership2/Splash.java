package mushirih.thoughtleadership2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import mushirih.thoughtleadership2.intro.WelcomeActivity;

/**
 * Created by p-tah on 25/01/2016.
 */
public class Splash extends AppCompatActivity {

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
           Intent newIntent=new Intent(getBaseContext(),WelcomeActivity.class);
            startActivity(newIntent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.splash);
//        ImageView splash= (ImageView) findViewById(R.id.imagesplash);
//        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
//        splash.startAnimation(animation);
        handler.postDelayed(runnable, 3000);

//         VideoView videoHolder = (VideoView) findViewById(R.id.spl);
//
//        try{
//  Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
//          + R.raw.splashh);
//
//  videoHolder.setVideoURI(video);
//   videoHolder.requestFocus();
//            videoHolder.setZOrderOnTop(true);
//  videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//      public void onCompletion(MediaPlayer mp) {
//          Intent newIntent = new Intent(getBaseContext(), Home.class);
//
//          startActivity(newIntent);
//      }
//
//  });
//
//  videoHolder.start();
// } catch(Exception ex) {
//     Intent newIntent=new Intent(getBaseContext(), Home.class);
//     startActivity(newIntent);
// }
}


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        finish();
    }

}
