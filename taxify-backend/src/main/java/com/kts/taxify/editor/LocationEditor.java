package com.kts.taxify.editor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class LocationEditor extends PropertiesEditor {

    private final ObjectMapper objectMapper;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            Location location = new Location();
            try {
                location = objectMapper.readValue(text, Location.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
            setValue(location);
        }
    }
}
