package edu.tacoma.uw.pbae.webserviceslab;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import edu.tacoma.uw.pbae.webserviceslab.course.Course;

import static edu.tacoma.uw.pbae.webserviceslab.CourseDetailFragment.COURSE_ITEM_SELECTED;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEditFragment extends Fragment {
    private final static String COURSE_EDIT_URL = "http://pbaeandroid.000webhostapp.com/android/editCourse.php?";
    private EditText mCourseIdEditText;
    private EditText mCourseShortDescEditText;
    private EditText mCourseLongDescEditText;
    private EditText mCoursePrereqsEditText;

    private CourseEditFragment.CourseEditListener mListener;

    public CourseEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_edit, container, false);
        mCourseIdEditText = (EditText) v.findViewById(R.id.edit_course_id);
        mCourseShortDescEditText = (EditText) v.findViewById(R.id.edit_course_short_desc);
        mCourseLongDescEditText = (EditText) v.findViewById(R.id.edit_course_long_desc);
        mCoursePrereqsEditText = (EditText) v.findViewById(R.id.edit_course_prereqs);

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        Button editCourseButton = (Button) v.findViewById(R.id.btnEditCourse);
        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick","clicked");
                String url = buildCourseURL(v);
                mListener.editCourse(url);
            }
        });

        return v;
    }
    private String buildCourseURL(View v) {
        StringBuilder sb = new StringBuilder(COURSE_EDIT_URL);
        try {
            String courseId = mCourseIdEditText.getText().toString();
            sb.append("id=");
            sb.append(URLEncoder.encode(courseId, "UTF-8"));
            String courseShortDesc = mCourseShortDescEditText.getText().toString();
            sb.append("&shortDesc=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));
            String courseLongDesc = mCourseLongDescEditText.getText().toString();
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));
            String coursePrereqs = mCoursePrereqsEditText.getText().toString();
            sb.append("&prereqs=");
            sb.append(URLEncoder.encode(coursePrereqs, "UTF-8"));
            Log.i("tag", sb.toString());
        } catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        Log.i("editFrag",sb.toString());
        return sb.toString();
    }

    public void updateView(Bundle bundle) {
        if (bundle != null) {
            mCourseIdEditText.setText(bundle.getString("id"));
            mCourseShortDescEditText.setText(bundle.getString("sDesc"));
            mCourseLongDescEditText.setText(bundle.getString("lDesc"));
            mCoursePrereqsEditText.setText(bundle.getString("prereqs"));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CourseAddFragment.CourseAddListener) {
            mListener = (CourseEditFragment.CourseEditListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null) {
            // Set course information based on argument passed
            updateView(args);
        }
    }

    public interface CourseEditListener {
        public void editCourse(String url);
    }
}
