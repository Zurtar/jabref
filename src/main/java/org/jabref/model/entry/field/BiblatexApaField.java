package org.jabref.model.entry.field;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jabref.model.entry.types.BiblatexApaEntryType;

public enum BiblatexApaField implements Field {

    AMENDMENT("amendment"),
    ARTICLE("article"),
    CITATION("citation"),
    CITATION_CITEORG("citation_citeorg"),
    CITATION_CITEDATE("citation_citedate", FieldProperty.DATE),
    CITATION_CITEINFO("citation_citeinfo"),
    SECTION("section", FieldProperty.NUMERIC),
    SOURCE("source");

    private final String name;
    private final String displayName;
    private final EnumSet<FieldProperty> properties;

    private static final Map<String, BiblatexApaField> NAME_TO_BIBLATEXTAPA_FIELD = new HashMap<>();

    static {
        for (BiblatexApaField field : BiblatexApaField.values()) {
            NAME_TO_BIBLATEXTAPA_FIELD.put(field.getName().toLowerCase(Locale.ROOT), field);
        }
    }

    BiblatexApaField(String name) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    BiblatexApaField(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    BiblatexApaField(String name, String displayName, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.of(first, rest);
    }

    BiblatexApaField(String name, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.of(first, rest);
    }

    public static <T> Optional<BiblatexApaField> fromName(T type, String name) {
        if (!(type instanceof BiblatexApaEntryType)) {
            // Also returns nothing if no type is given.
            // Reason: The field should also be recognized in the presence of a BiblatexApa entry type.
            return Optional.empty();
        }

        return Optional.ofNullable(NAME_TO_BIBLATEXTAPA_FIELD.get(name.toLowerCase(Locale.ROOT)));
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
