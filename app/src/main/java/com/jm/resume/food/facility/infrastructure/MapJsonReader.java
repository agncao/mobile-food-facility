package com.jm.resume.food.facility.infrastructure;

import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Collections;
import java.util.Map;
import com.alibaba.fastjson.JSON;

@Slf4j
@ReadingConverter
public class MapJsonReader implements Converter<Json, Map<String, Object>> {

    public MapJsonReader() {}

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> convert(Json json) {
        String s = json.asString();
        return s.equals("{}") ? Collections.emptyMap()
            : JSON.parseObject(s, Map.class);
    }
}
