package com.antonioleiva.recycledviewextensions.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.antonioleiva.recycledviewextensions.BaseLayoutManager;
import com.antonioleiva.recycledviewextensions.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OrientationDialogFragment.Listener {

    private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";

    private int mOrientation = BaseLayoutManager.VERTICAL;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        final MyRecyclerAdapter adapter;
        mRecyclerView.setAdapter(adapter = new MyRecyclerAdapter(createMockList(), R.layout.item));
        updateLayoutManager();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ViewModel>() {
            @Override public void onItemClick(View view, ViewModel viewModel) {
                adapter.remove(viewModel);
            }
        });
    }

    private void updateLayoutManager() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GridLayoutManager.DEFAULT_COLUMNS, mOrientation));
    }

    private List<ViewModel> createMockList() {
        List<ViewModel> items = new ArrayList<ViewModel>();
        for (int i = 0; i < 20; i++) {
            items.add(new ViewModel(i, "Item " + (i + 1), MOCK_URL + (i % 10 + 1)));
        }
        return items;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_orientation) {
            showOrientationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrientationDialog() {
        OrientationDialogFragment dlg = OrientationDialogFragment.newInstance(mOrientation);
        dlg.show(getFragmentManager(), "orientationDlg");
    }

    @Override
    public void onNewOrientationSelected(int orientation) {
        if (orientation != mOrientation) {
            mOrientation = orientation;
            updateLayoutManager();
        }
    }
}
