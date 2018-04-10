package edu.tacoma.uw.css.pbae.dialogslab;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DialogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
    }

    public void launch(View view) {
        DialogFragment fragment = null;
        if (view.getId() == R.id.btn_fire_missiles) {
            fragment = new FireMissilesDialogFragment();
        } else if (view.getId() == R.id.btn_launch_colors) {
            fragment = new ListDialogFragment();
        } else if (view.getId() == R.id.btn_launch_toppings) {
            fragment = new MultiListDialogFragment();
        } else if (view.getId() == R.id.btn_custom_signin) {
            fragment = new CustomDialogFragment();
        } else if (view.getId() == R.id.btn_notification) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                    "default")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Hello")
                    .setContentText("How are ya?")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1991, mBuilder.build());
        }


        if (fragment != null)
            fragment.show(getSupportFragmentManager(), "launch");
    }

}
