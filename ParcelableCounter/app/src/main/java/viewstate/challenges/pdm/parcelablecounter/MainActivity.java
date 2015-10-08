package viewstate.challenges.pdm.parcelablecounter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String COUNTER_STATE = "MainActivity.CounterState";

    private TextView counterView;
    private Counter counter;


    private void updateView(int counterValue) {
        counterView.setText(Integer.toString(counterValue));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = savedInstanceState == null ? new Counter(0, 10)
                : (Counter) savedInstanceState.getParcelable(COUNTER_STATE);
        counterView = (TextView) findViewById(R.id.msg_counter);
        updateView(counter.getValue());

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(COUNTER_STATE, counter);
        super.onSaveInstanceState(outState);
    }
}
