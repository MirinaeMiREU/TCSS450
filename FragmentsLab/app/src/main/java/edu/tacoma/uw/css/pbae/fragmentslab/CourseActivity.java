package edu.tacoma.uw.css.pbae.fragmentslab;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.tacoma.uw.css.pbae.fragmentslab.course.CourseContent;

public class CourseActivity extends AppCompatActivity implements CourseListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        if (findViewById(R.id.course_fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.course_fragment_container, new CourseListFragment())
                                       .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(CourseContent.CourseItem item) {
        CourseDetailFragment courseDetailFragment = null;

        if (findViewById(R.id.course_fragment_container) == null) {
            // If courseDetail frag is available, we're in two-pane layout...
            //Capture the course fragment from the activity layout
            courseDetailFragment = (CourseDetailFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.course_detail_frag);
            // Call a method in the course detail fragment to update its content
                courseDetailFragment.updateCourseItemView(item);
        } else {

            // If the frag is not available, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected course
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            courseDetailFragment = CourseDetailFragment.getCourseDetailFragment(item);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.course_fragment_container, courseDetailFragment)
                    .addToBackStack(null);
            // Commit the transaction
            //   transaction.commit();
        }
    }
}
