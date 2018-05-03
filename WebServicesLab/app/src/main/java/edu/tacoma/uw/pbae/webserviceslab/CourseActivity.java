package edu.tacoma.uw.pbae.webserviceslab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.pbae.webserviceslab.course.Course;

public class CourseActivity extends AppCompatActivity
                            implements CourseListFragment.OnListFragmentInteractionListener,
                                       CourseAddFragment.CourseAddListener,
                                       CourseEditFragment.CourseEditListener {
    private CourseListFragment mList;
    private CourseDetailFragment mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseAddFragment courseAddFragment = new CourseAddFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, courseAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        mList = new CourseListFragment();
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.fragment_container, mList)
                                   .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addCourse(String url) {
        AddCourseTask task = new AddCourseTask();
        task.execute(new String[]{url.toString()});// Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void editCourse(String url) {
        EditCourseTask task = new EditCourseTask();
        task.execute(new String[]{url.toString()});
        mDetail.setArguments(null);
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onListFragmentInteraction(Course item) {
        mDetail = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(CourseDetailFragment.COURSE_ITEM_SELECTED, item);
        mDetail.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mDetail)
                .addToBackStack(null)
                .commit();
    }

    private class AddCourseTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        /**     * It checks to see if there was a problem with the URL(Network) which is when an
         *  * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         *  * If not, it displays the exception.
         *  *
         *  * @param result
         *  */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = (String) jsonObject.get("result");
                    if (status.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Course successfully added!"
                                , Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add: "
                                + jsonObject.get("error")
                                , Toast.LENGTH_LONG)
                                .show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Something wrong with the data " +
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
        }
    }

    private class EditCourseTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to edit course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        /**     * It checks to see if there was a problem with the URL(Network) which is when an
         *  * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         *  * If not, it displays the exception.
         *  *
         *  * @param result
         *  */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Course successfully edited!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
