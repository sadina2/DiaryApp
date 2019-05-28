package com.example.korisnik.diaryapp;


import android.content.Intent;
import android.support.v4.app.Fragment;

public class DiaryListActivity extends SingleFragmentActivity
    implements DiaryListFragment.Callbacks, DiaryFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new DiaryListFragment();

    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onDiarySelected(Diary diary) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = DiaryPagerActivity.newIntent(this, diary.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = DiaryFragment.newInstance(diary.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onDiaryUpdated(Diary diary) {
        DiaryListFragment listFragment = (DiaryListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}