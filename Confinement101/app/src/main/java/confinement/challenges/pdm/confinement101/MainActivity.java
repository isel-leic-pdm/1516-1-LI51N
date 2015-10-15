package confinement.challenges.pdm.confinement101;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity used to illustrate the consequences of thread-confinement.
 *
 * Remember this: All calbacks (unless stated otherwise) are sequentially executed in the process
 * main thread. This means that callback execution is constrained and must return as soon as
 * possible. Long running operations or I/O are therefore forbidden.
 *
 * The goal of this demo is to illustrate how to address the issue, using AsyncTask, if one actually
 * needs to perform a long running operation, while updating the UI with its progress.
 *
 * The demo also illustrates a fundamental flaw when a configuration change occurs (i.e. screen
 * rotation) while the long running operation is in progress. Try it out... ;)
 * The solution to this problem is not presented herein. A demo on that problem will come later.
 *
 *
 * Disclaimer: This is a demo and therefore its purpose is to illustrate the use of mechanisms.
 * It is not about software design. Mindlessly adapting these demos to your solutions is not
 * an appropriate approach to software development.
 */
public class MainActivity extends AppCompatActivity {

    /** The progress value for a completed operation. */
    private static final int MAX_PROGRESS = 100;
    /** Progress is updated by the following increment value. */
    private static final int STEP_PROGRESS = 5;

    /** Key used to identify progress state in the view's state Bundle. */
    private static final String STATE = "ProgressState";

    /** The view used to display progress. */
    private TextView progressView;

    /**
     * Helper method used to update the UI whenever the current progress changes.
     * @param progress The current progress value.
     */
    private void updateProgressView(int progress) {
        progressView.setText(Integer.toString(progress));
    }

    /**
     * Helper method used to simulate a step (i.e. iteration) of the long running operation.
     * Notice that this method is simulating a long operation (i.e. at least 500 ms long)
     */
    private static void doStep() {
        // Disclaimer. This is a demo! The code below is terrible in all aspects!
        try { Thread.sleep(500); }
        catch (InterruptedException ignored) { }
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lets restore UI state, if present
        progressView = (TextView) findViewById(R.id.msg_progress);
        if(savedInstanceState != null)
            progressView.setText(Integer.toString(savedInstanceState.getInt(STATE)));

        // Setup behaviour of the start button in order to simulate the long running operation
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                // What if an operation is already in progress? We didn't address this issue.

                // Define the long running operation and corresponding UI updates and start it.
                // Once it is completed (either successfully or canceled), the async task instance
                // is discarded (they can only be used once)
                (new AsyncTask<Void, Integer, Integer>() {
                    /** {@inheritDoc} */
                    @Override
                    protected Integer doInBackground(Void... params) {
                        // Specifies the long running work. The method is executed in an alternative
                        // thread, provided by the AsyncTask implementation.
                        // Notice that there is no shared mutable state (at the application level)
                        // between the executing thread and the main thread.
                        int progress = 0;
                        while(progress <= MAX_PROGRESS) {
                            Log.v("PROGRESS", "Progress is " + progress + " in thread "
                                + Thread.currentThread().getName() + " " + Thread.currentThread().getId());

                            // Request progress update. The method can be safely called from this
                            // thread and promotes the forwarding of the request to the main thread.
                            publishProgress(progress);
                            progress += STEP_PROGRESS;
                            doStep();
                        }
                        return MAX_PROGRESS;
                    }

                    /** {@inheritDoc} */
                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        // Callback method used to update the UI whenever a progress update is
                        // requested. Executed in the main thread and therefore we can safely update
                        // the UI.
                        updateProgressView(values[0]);
                        Log.v("PROGRESS", "Progress is " + values[0] + " in thread "
                                + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
                    }

                    /** {@inheritDoc} */
                    @Override
                    protected void onPostExecute(Integer result) {
                        // Callback method used to update the UI when the operation is successfully
                        // completed. It is executed in the main thread and therefore we can safely
                        // update the UI. (in this case, by presenting a Toast)
                        Toast.makeText(
                                MainActivity.this, "Done : " + result, Toast.LENGTH_LONG
                        ).show();
                    }
                }).execute();
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE, Integer.parseInt(progressView.getText().toString()));
    }
}
