package demos.pdm.helloandroid;

import android.os.Bundle;
import android.view.View;

/**
 * Activity used to illustrate the consequences of a managed life-cycle. It also demonstrates
 * how to use locale dependent strings (see res/values/strings.xml and res/values-pt/strings.xml).
 *
 * Disclaimer: This is a demo and therefore its purpose is to illustrate the use of mechanisms.
 * It is not about software design. Mindlessly adapting these demos to your solutions is not
 * an appropriate approach to software development.
 */
public class MainActivity extends LoggingActivity {

    /** Key used to identify the object containing the view state to be stored */
    private static final String STATE_KEY = "MainActivity.UIState";

    /** {@inheritDoc} */
    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            // Load state from bundle, if one has been passed in
            final int msgBoxVisibility = savedInstanceState.getInt(STATE_KEY);
            // Update view accordingly
            findViewById(R.id.msg_box).setVisibility(msgBoxVisibility);
        }

        findViewById(R.id.say_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                findViewById(R.id.msg_box).setVisibility(View.VISIBLE);
            }
        });


    }

    /**
     * Callback method that gives the opportunity to save view state in the given {@link Bundle}.
     * @param outState The {@link Bundle} instance where the view state is to be stored.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Fetch the view's state
        final int msgBoxVisibility = findViewById(R.id.msg_box).getVisibility();
        // Store it in the bundle
        outState.putInt(STATE_KEY, msgBoxVisibility);
    }
}
