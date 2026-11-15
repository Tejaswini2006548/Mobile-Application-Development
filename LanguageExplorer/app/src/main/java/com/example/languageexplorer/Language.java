
package com.example.languageexplorer;

public class Language {

    private final String name;
    private final String icon;
    private final String description;
    private final String feature;

    public Language(String name, String icon,
                    String description, String feature) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.feature = feature;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getFeature() {
        return feature;
    }

    @Override
    public String toString() {
        return icon + "   " + name;
    }
}
