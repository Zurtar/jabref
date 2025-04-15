package org.jabref.model.entry.field;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jabref.model.entry.types.BiblatexSoftwareEntryType;

public enum BiblatexSoftwareField implements Field {

    HALID("hal_id"),
    HALVERSION("hal_version"),
    INTRODUCEDIN("introducedin"),
    LICENSE("license"),
    RELATEDTYPE("relatedtype"),
    RELATEDSTRING("relatedstring"),
    REPOSITORY("repository"),
    SWHID("swhid");

    private final String name;
    private final String displayName;
    private final EnumSet<FieldProperty> properties;

    private static final Map<String, BiblatexSoftwareField> NAME_TO_BIBLATEX_SOFTWARE_FIELD = new HashMap<>();

    static {
        for (BiblatexSoftwareField field : BiblatexSoftwareField.values()) {
            NAME_TO_BIBLATEX_SOFTWARE_FIELD.put(field.getName().toLowerCase(Locale.ROOT), field);
        }
    }

    BiblatexSoftwareField(String name) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    BiblatexSoftwareField(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    BiblatexSoftwareField(String name, String displayName, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.of(first, rest);
    }

    BiblatexSoftwareField(String name, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.of(first, rest);
    }

    public static <T> Optional<BiblatexSoftwareField> fromName(T type, String name) {
        if (!(type instanceof BiblatexSoftwareEntryType)) {
            return Optional.empty();
        }

        return Optional.ofNullable(NAME_TO_BIBLATEX_SOFTWARE_FIELD.get(name.toLowerCase(Locale.ROOT)));
    }

    @Override
    public EnumSet<FieldProperty> getProperties() {
        return properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isStandardField() {
        return false;
    }

    @Override
    public String getDisplayName() {
        if (displayName == null) {
            return Field.super.getDisplayName();
        } else {
            return displayName;
        }
    }
}
