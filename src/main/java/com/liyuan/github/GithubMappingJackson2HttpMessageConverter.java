package com.liyuan.github;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class GithubMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public GithubMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>(1);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }
}
