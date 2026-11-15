
package com.example.languageexplorer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity
        implements LanguageListFragment.OnLanguageSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Programming Languages Explorer");

        if (savedInstanceState == null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction =
                    manager.beginTransaction();

            transaction.add(
                    R.id.list_container,
                    new LanguageListFragment());

            transaction.add(
                    R.id.details_container,
                    new LanguageDetailsFragment());

            transaction.commit();
        }
    }

    @Override
    public void onLanguageSelected(Language language) {

        LanguageDetailsFragment details =
                (LanguageDetailsFragment) getFragmentManager()
                        .findFragmentById(R.id.details_container);

        if (details != null) {
            details.showLanguage(language);
        }
    }
}
