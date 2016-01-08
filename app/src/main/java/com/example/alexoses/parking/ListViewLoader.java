package com.example.alexoses.parking;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by Alex on 07/01/2016.
 */
public class ListViewLoader extends ListActivity implements LoaderManager.LoaderCallbacks{
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
