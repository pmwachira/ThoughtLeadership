package mushirih.thoughtleadership2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
        setContentView(R.layout.splash);
        ImageView splash= (ImageView) findViewById(R.id.imagesplash);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
        splash.startAnimation(animation);
        handler.postDelayed(runnable,3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
