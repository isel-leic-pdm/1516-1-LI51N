package viewstate.challenges.pdm.parcelablecounter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Activity used to illustrate view state maintenace using custom state objects.
 * The implementation is inspired in the MVC design pattern: the Model is segregated from the
 * remaining participants. Notice that the View and Controller roles are nor clearly separated.
 *
 * Don't mind AppCompatActivity because it is not fundamental to understand Android's programming
 * model. For that purpose one can Assume that {@link }MainActivity} directly extends {@link Activity}
 *
 * Disclaimer: This is a demo and therefore its purpose is to illustrate the use of mechanisms.
 * It is not about software design. Mindlessly adapting these demos to your solutions is not
 * an appropriate approach to software development.
 */
public class MainActivity extends AppCompatActivity {

    /** Key used to identify the object containing the view state to be stored */
    private static final String COUNTER_STATE = "MainActivity.CounterState";

    /**
     * The counter view. At least, a part of it. One could argue that the counter view should
     * be composed of both the view of its value and of the buttons to trigger counter modification
     * events. This, of course, would depend on the context.
     */
    private TextView counterView;

    /** The counter's state and behavior, a.k.a Model. */
    private Counter counter;

    /**
     * Helper method used to update the view whenever the counter value is modified.
     * @param counterValue The new counter value.
     */
    private void updateView(int counterValue) {
        counterView.setText(Integer.toString(counterValue));
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load state from bundle, if one has been passed in
        counter = savedInstanceState == null ? new Counter(0, 10)
                : (Counter) savedInstanceState.getParcelable(COUNTER_STATE);

        // Update view accordingly. Because findViewById is usually expensive, and because
        // we will update it in every button click, we hold on to it as an instance field.
        counterView = (TextView) findViewById(R.id.msg_counter);
        updateView(counter.getValue());

        // Setup UI behaviour
        findViewById(R.id.button_inc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                updateView(counter.increment());
            }
        });

        findViewById(R.id.button_dec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                updateView(counter.decrement());
            }
        });
    }

    /**
     * Callback method that gives the opportunity to save view state in the given {@link Bundle}.
     * Notice that {@link Bundle} instances may cross process boundaries, hence the additional
     * requirements to the contained elements (e.g. {@link Parcelable}).
     * @param outState The {@link Bundle} instance where the view state is to be stored.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store the counter's state in the bundle
        outState.putParcelable(COUNTER_STATE, counter);
        // Delegate remaining actions (if any) to the inherited implementation
        super.onSaveInstanceState(outState);
    }
}
