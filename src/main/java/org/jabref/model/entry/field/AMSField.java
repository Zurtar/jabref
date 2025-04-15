package org.jabref.model.entry.field;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public enum AMSField implements Field {

    FJOURNAL("fjournal");

    private final String name;
    private final String displayName;
    private final EnumSet<FieldProperty> properties;

    private static final Map<String, AMSField> NAME_TO_AMS_FIELD = new HashMap<>();

    static {
        for (AMSField field : AMSField.values()) {
            NAME_TO_AMS_FIELD.put(field.getName().toLowerCase(Locale.ROOT), field);
        }
    }

    AMSField(String name) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    AMSField(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.noneOf(FieldProperty.class);
    }

    AMSField(String name, String displayName, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = displayName;
        this.properties = EnumSet.of(first, rest);
    }

    AMSField(String name, FieldProperty first, FieldProperty... rest) {
        this.name = name;
        this.displayName = null;
        this.properties = EnumSet.of(first, rest);
    }

    public static <T> Optional<AMSField> fromName(T type, String name) {
        return Optional.ofNullable(NAME_TO_AMS_FIELD.get(name.toLowerCase(Locale.ROOT)));
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
