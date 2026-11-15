
package com.example.languageexplorer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LanguageListFragment extends Fragment {

    public interface OnLanguageSelectedListener {
        void onLanguageSelected(Language language);
    }

    private OnLanguageSelectedListener listener;

    private final Language[] languages = {
            new Language("Java", "☕",
                    "A popular object-oriented programming language.",
                    "Used for Android applications and enterprise software."),

            new Language("Python", "🐍",
                    "A simple and easy-to-learn programming language.",
                    "Used in AI, machine learning and data science."),

            new Language("Kotlin", "🟣",
                    "A modern programming language developed by JetBrains.",
                    "Widely used for Android development."),

            new Language("C++", "💻",
                    "An extension of the C programming language.",
                    "Used in games and competitive programming."),

            new Language("JavaScript", "🌐",
                    "A scripting language used for web development.",
                    "Used to create interactive websites."),

            new Language("C", "⚙️",
                    "A general-purpose procedural programming language.",
                    "Used in operating systems and embedded systems.")
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnLanguageSelectedListener) {
            listener = (OnLanguageSelectedListener) activity;
        } else {
            throw new RuntimeException(
                    "Activity must implement OnLanguageSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_language_list,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.languageList);

        ArrayAdapter<Language> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        languages);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                (parent, row, position, id) -> {
                    if (listener != null) {
                        listener.onLanguageSelected(
                                languages[position]);
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
