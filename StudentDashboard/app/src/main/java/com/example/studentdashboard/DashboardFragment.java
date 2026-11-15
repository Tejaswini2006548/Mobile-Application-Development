
package com.example.studentdashboard;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DashboardFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,
                                    MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.add(Menu.NONE, 1001, Menu.NONE, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1001) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Help")
                    .setMessage("Use the three-dot menu for options. "
                            + "Long press the student record for "
                            + "context menu actions. Use the Popup "
                            + "Menu for quick actions.")
                    .setPositiveButton("OK", null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
