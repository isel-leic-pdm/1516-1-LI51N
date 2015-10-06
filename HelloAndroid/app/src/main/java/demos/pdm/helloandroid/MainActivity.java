package demos.pdm.helloandroid;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends LoggingActivity {

    private static final String STATE_KEY = "MainActivity.UIState";

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            final int msgBoxVisibility = savedInstanceState.getInt(STATE_KEY);
            findViewById(R.id.msg_box).setVisibility(msgBoxVisibility);
        }

        findViewById(R.id.say_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                findViewById(R.id.msg_box).setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final int msgBoxVisibility = findViewById(R.id.msg_box).getVisibility();
        outState.putInt(STATE_KEY, msgBoxVisibility);
    }
}
