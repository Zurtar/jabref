package org.jabref.model.entry.field;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jabref.model.entry.KeywordList;

public enum SpecialField implements Field {

    PRINTED("printed",
            SpecialFieldValue.PRINTED
    ),

    PRIORITY("priority",
            SpecialFieldValue.CLEAR_PRIORITY,
            SpecialFieldValue.PRIORITY_HIGH,
            SpecialFieldValue.PRIORITY_MEDIUM,
            SpecialFieldValue.PRIORITY_LOW
    ),

    QUALITY("qualityassured",
            SpecialFieldValue.QUALITY_ASSURED
    ),

    RANKING("ranking",
            SpecialFieldValue.CLEAR_RANK,
            SpecialFieldValue.RANK_1,
            SpecialFieldValue.RANK_2,
            SpecialFieldValue.RANK_3,
            SpecialFieldValue.RANK_4,
            SpecialFieldValue.RANK_5
    ),

    READ_STATUS("readstatus",
            SpecialFieldValue.CLEAR_READ_STATUS,
            SpecialFieldValue.READ,
            SpecialFieldValue.SKIMMED
    ),

    RELEVANCE("relevance",
            SpecialFieldValue.RELEVANT
    );

    private final List<SpecialFieldValue> values;
    private final KeywordList keywords;
    private final HashMap<String, SpecialFieldValue> map;
    private final String fieldName;

    private static final Map<String, SpecialField> NAME_TO_SPECIAL_FIELD = new HashMap<>();

    static {
        for (SpecialField field : SpecialField.values()) {
            NAME_TO_SPECIAL_FIELD.put(field.getName().toLowerCase(Locale.ROOT), field);
        }
    }

    SpecialField(String fieldName, SpecialFieldValue... values) {
        this.fieldName = fieldName;
        this.values = new ArrayList<>();
        this.keywords = new KeywordList();
        this.map = new HashMap<>();
        for (SpecialFieldValue value : values) {
            this.values.add(value);
            value.getKeyword().ifPresent(keywords::add);
            value.getFieldValue().ifPresent(fieldValue -> map.put(fieldValue, value));
        }
    }

    public List<SpecialFieldValue> getValues() {
        return this.values;
    }

    public KeywordList getKeyWords() {
        return this.keywords;
    }

    public static Optional<SpecialField> fromName(String name) {
        return Optional.ofNullable(NAME_TO_SPECIAL_FIELD.get(name.toLowerCase(Locale.ROOT)));
    }

    public boolean isSingleValueField() {
        return this.values.size() == 1;
    }

    public Optional<SpecialFieldValue> parseValue(String value) {
        return Optional.ofNullable(map.get(value));
    }

    @Override
    public EnumSet<FieldProperty> getProperties() {
        return EnumSet.noneOf(FieldProperty.class);
    }

    @Override
    public String getName() {
        return fieldName;
    }

    @Override
    public boolean isStandardField() {
        return false;
    }
}
