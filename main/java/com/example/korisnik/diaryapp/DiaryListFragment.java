package com.example.korisnik.diaryapp;


import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class DiaryListFragment extends Fragment {

    private RecyclerView mDiaryRecyclerView;
    private DiaryAdapter mAdapter;
    private TextView mTitleTextView, mDateTextView;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onDiarySelected(Diary diary);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, container, false);
        mDiaryRecyclerView = (RecyclerView) view
                .findViewById(R.id.diary_recycler_view);
        mDiaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_diary_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_story:
                Diary diary= new Diary();
                DiaryLab.get(getActivity()).addDiary(diary);

                updateUI();
                mCallbacks.onDiarySelected(diary);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }



    public void updateUI() {
        DiaryLab diaryLab = DiaryLab.get(getActivity());
        List<Diary> stories = diaryLab.getDiary();
        if (mAdapter == null) {
            mAdapter = new DiaryAdapter(stories);
            mDiaryRecyclerView.setAdapter(mAdapter);

        }
        else
        {
            mAdapter.setStories(stories);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DiaryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Diary mDiary;

        public void bind(Diary diary) {
            mDiary = diary;
            mTitleTextView.setText(mDiary.getTitle());
            mDateTextView.setText(mDiary.getDate().toString());

        }

        public DiaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_diary, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.diary_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.diary_date);
        }
        @Override
        public void onClick(View view) {
            //Intent intent = MainActivity.newIntent(getActivity(), mDiary.getId());
           // Intent intent = DiaryPagerActivity.newIntent(getActivity(), mDiary.getId());
            //startActivity(intent);
            mCallbacks.onDiarySelected(mDiary);
        }
    }

    private class DiaryAdapter extends RecyclerView.Adapter<DiaryHolder> {
        private List<Diary> mDiary;
        public DiaryAdapter(List<Diary> stories) {
            mDiary = stories;
        }


        @Override
        public DiaryHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            return new DiaryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DiaryHolder holder, int position) {
            Diary diary=mDiary.get(position);
            holder.bind(diary);
        }

        @Override
        public int getItemCount() {
            return mDiary.size();
        }
        public void setStories(List<Diary> stories) {
            mDiary = stories;
        }
    }
}
