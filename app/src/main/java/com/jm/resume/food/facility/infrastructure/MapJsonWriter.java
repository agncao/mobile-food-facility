package com.jm.resume.food.facility.infrastructure;

import com.alibaba.fastjson.JSON;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@Slf4j
@WritingConverter
public class MapJsonWriter implements Converter<Map<String, Object>, Json> {
    public MapJsonWriter() {
    }

    @Override
    public Json convert(Map<String, Object> source) {
        return source.isEmpty() ? Json.of("{}")
            : Json.of(JSON.toJSONString(source));
    }
}
