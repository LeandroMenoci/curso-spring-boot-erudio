package com.curso.serialization.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter{

    private static Include NON_NULL;

    public YamlJackson2HttpMessageConverter() {
        super(new YAMLMapper().setSerializationInclusion(NON_NULL), 
            MediaType.parseMediaType("application/x-yaml"));
    }

}
