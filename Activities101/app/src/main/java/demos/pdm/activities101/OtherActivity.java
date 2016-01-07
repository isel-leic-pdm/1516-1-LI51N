package demos.pdm.activities101;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Another Activity used to illustrate user tasks and back-stack.
 *
 * Disclaimer: This is a demo and therefore its purpose is to illustrate the use of mechanisms.
 * It is not about software design. Mindlessly adapting these demos to your solutions is not
 * an appropriate approach to software development.
 */
public class OtherActivity extends LifecycleActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        (new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try { Thread.sleep(3000); }
                catch (InterruptedException _) { }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(isFinishing())
                    Log.v("AsyncTask", "onPostExecute - isFinishing");
                else
                    Log.v("AsyncTask", "onPostExecute");
            }
        }).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try { Thread.sleep(3000); }
        catch (InterruptedException _) { }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try { Thread.sleep(3000); }
        catch (InterruptedException _) { }
    }
}
