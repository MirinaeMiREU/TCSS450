package edu.tacoma.uw.pbae.webserviceslab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.tacoma.uw.pbae.webserviceslab.course.Course;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class CourseDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public final static String COURSE_ITEM_SELECTED = "course_selected";

    // TODO: Rename and change types of parameters
    private TextView mCourseIdTextView;
    private TextView mCourseShortDescTextView;
    private TextView mCourseLongDescTextView;
    private TextView mCoursePrereqsTextView;

    //private OnFragmentInteractionListener mListener;

    public CourseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course_detail, container, false);
        mCourseIdTextView = (TextView) view.findViewById(R.id.course_item_id);
        mCourseShortDescTextView = (TextView) view.findViewById(R.id.course_short_desc);
        mCourseLongDescTextView = (TextView) view.findViewById(R.id.course_long_desc);
        mCoursePrereqsTextView = (TextView) view.findViewById(R.id.course_prereqs);

        Button editCourseButton = (Button) view.findViewById(R.id.btnEdit);
        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("editOnClick","clicked");
                CourseEditFragment editFrag = new CourseEditFragment();
                Bundle args = new Bundle();
                args.putString("id", mCourseIdTextView.getText().toString());
                args.putString("sDesc", mCourseShortDescTextView.getText().toString());
                args.putString("lDesc", mCourseLongDescTextView.getText().toString());
                args.putString("prereqs", mCoursePrereqsTextView.getText().toString());
                editFrag.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);floatingActionButton.show();
        return view;
    }

    public void updateView(Course course) {
        if (course != null) {
            mCourseIdTextView.setText(course.getCourseId());
            mCourseShortDescTextView.setText(course.getShortDescription());
            mCourseLongDescTextView.setText(course.getLongDescription());
            mCoursePrereqsTextView.setText(course.getPrereqs());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("detail", "resume");
        Bundle args = getArguments();
        if (args != null) {
            // Set course information based on argument passed
            updateView((Course) args.getSerializable(COURSE_ITEM_SELECTED));
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
