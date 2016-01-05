package pdm.demos.interactionmodels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *
 */
public class MyNetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Connectivity State change", Toast.LENGTH_LONG).show();
        context.startService(MyIntentService.ContractHelper.makeOneWayIntent(
                context, "lisbon, PT"));
    }
}
