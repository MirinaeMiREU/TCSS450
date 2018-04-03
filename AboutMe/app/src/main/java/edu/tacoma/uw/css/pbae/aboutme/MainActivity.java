package edu.tacoma.uw.css.pbae.aboutme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void textButton(View view) {
        Intent intent = new Intent(this, TextActivity.class);
        startActivity(intent);
    }

    public void imageButton(View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    public void webButton(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                                   Uri.parse("http://developer.android.com/index.html"));
        startActivity(intent);
    }

    public void toastButton(View view) {
        String str = "Toast for those who mean the Most!";
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void dialogButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Is 5 + 5 = 10?");

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),
                                 "You're not very smart",
                                       Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),
                                "Correct!",
                                Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
