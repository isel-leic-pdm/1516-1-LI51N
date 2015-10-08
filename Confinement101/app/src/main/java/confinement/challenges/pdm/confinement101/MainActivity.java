package confinement.challenges.pdm.confinement101;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_PROGRESS = 100;
    private static final int STEP_PROGRESS = 5;
    private TextView progressView;

    private void updateProgressView(int progress) {
        progressView.setText(Integer.toString(progress));
    }

    private static void doStep() {
        try { Thread.sleep(2000); }
        catch (InterruptedException ignored) { }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = (TextView) findViewById(R.id.msg_progress);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                (new AsyncTask<Void, Integer, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... params) {
                        int progress = 0;
                        while(progress <= MAX_PROGRESS) {
                            publishProgress(progress);
                            progress += STEP_PROGRESS;
                            doStep();
                        }
                        return MAX_PROGRESS;
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        updateProgressView(values[0]);
                    }
                }).execute();
            }
        });
    }
}
