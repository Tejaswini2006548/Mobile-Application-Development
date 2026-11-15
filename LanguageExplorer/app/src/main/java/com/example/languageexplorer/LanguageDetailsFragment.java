
package com.example.languageexplorer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LanguageDetailsFragment extends Fragment {

    private TextView tvIcon;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvFeature;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_language_details,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvIcon = view.findViewById(R.id.tvIcon);
        tvName = view.findViewById(R.id.tvLanguageName);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvFeature = view.findViewById(R.id.tvFeature);
    }

    public void showLanguage(Language language) {
        if (tvName == null) {
            return;
        }

        tvIcon.setText(language.getIcon());
        tvName.setText(language.getName());
        tvDescription.setText(language.getDescription());
        tvFeature.setText(language.getFeature());
    }
}
