package com.example.korisnik.diaryapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class DiaryFragment extends Fragment {

    private static final String ARG_DIARY_ID = "diary_id";
    private static final String DIALOG_DATE = "DialogDate";


    private static final int REQUEST_DATE = 0;



    private Diary mDiary;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mStory;
    private Button mStoryButton;

    private Callbacks mCallbacks;
    private Button mTimeButton;

    public interface Callbacks {
        void onDiaryUpdated(Diary diary);
    }

    public static DiaryFragment newInstance(UUID diaryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIARY_ID, diaryId);

        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        UUID diaryId = (UUID) getArguments().getSerializable(ARG_DIARY_ID);
        mDiary = DiaryLab.get(getActivity()).getDiary(diaryId);

        setHasOptionsMenu(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        DiaryLab.get(getActivity())
                .updateDiary(mDiary);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary, container, false);

        mTitleField = (EditText) v.findViewById(R.id.title);
        mTitleField.setText(mDiary.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mDiary.setTitle(s.toString());
                updateDiary();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.date);
        updateDate();


        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mDiary.getDate());
                dialog.setTargetFragment(DiaryFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });


        mTimeButton = (Button) v.findViewById(R.id.time);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent calender = new Intent(Intent.ACTION_EDIT);
                calender.setType("vnd.android.cursor.item/event");
                calender.putExtra("title", "");
                calender.putExtra("beginTime", new Date().getTime());
                calender.putExtra("endTime", new Date().getTime()+ DateUtils.HOUR_IN_MILLIS);

                startActivity(calender);

            }
        });



        mStory = (EditText) v.findViewById(R.id.solved);
        mStory.setText(mDiary.getStory());
        mStory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mDiary.setStory(s.toString());
                updateDiary();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mStoryButton = (Button) v.findViewById(R.id.share);
        mStoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getStory());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.moja_prica));
               i = Intent.createChooser(i, getString(R.string.share));
                startActivity(i);
            }
        });



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDiary.setDate(date);
            updateDiary();
           // mDateButton.setText(mDiary.getDate().toString());

            updateDate();
        }




    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_story_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.delete_story_action:
               UUID storyId = mDiary.getId();
                DiaryLab.get(getActivity()).deleteStory(storyId);

                Toast.makeText(getActivity(), R.string.delete_story, Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void updateDiary() {

        DiaryLab.get(getActivity()).updateDiary(mDiary);
        mCallbacks.onDiaryUpdated(mDiary);
    }

    private void updateDate() {
        mDateButton.setText(mDiary.getDate().toString());
}




        private String getStory() {

        String story = mDiary.getStory();
        String dateFormat = "EEE, MMM d, ''yy";//"EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat,
                mDiary.getDate()).toString();

        String report = getString(R.string.story,
                mDiary.getTitle(), dateString, story);
        return report;
    }
}
