package com.example.korisnik.diaryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;


public class DiaryPagerActivity extends AppCompatActivity
    implements DiaryFragment.Callbacks {

    private static final String EXTRA_DIARY_ID =
            "com.example.korisnik.diaryapp.diary_id";

    private ViewPager mViewPager;
    private List<Diary> mDiary;

    public static Intent newIntent(Context packageContext, UUID diaryId) {
        Intent intent = new Intent(packageContext, DiaryPagerActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_pager);

        UUID diaryId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_DIARY_ID);

        mViewPager = (ViewPager) findViewById(R.id.diary_view_pager);
        mDiary = DiaryLab.get(this).getDiary();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Diary diary = mDiary.get(position);
                return DiaryFragment.newInstance(diary.getId());
            }
            @Override
            public int getCount() {
                return mDiary.size();
            }
        });

        for (int i = 0; i < mDiary.size(); i++) {
            if (mDiary.get(i).getId().equals(diaryId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
    @Override
    public void onDiaryUpdated(Diary diary){

    }
}
