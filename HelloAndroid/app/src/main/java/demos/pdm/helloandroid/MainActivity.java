package demos.pdm.helloandroid;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends LoggingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.say_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                findViewById(R.id.msg_box).setVisibility(View.VISIBLE);
            }
        });

    }
}
