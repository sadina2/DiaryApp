package com.example.korisnik.diaryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    private static final String EXTRA_DIARY_ID =
            "com.example.korisnik.diaryapp.diary_id";
    public static Intent newIntent(Context packageContext, UUID diaryId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID diaryID= (UUID) getIntent()
                .getSerializableExtra(EXTRA_DIARY_ID);
        return DiaryFragment.newInstance(diaryID);
    }


}
