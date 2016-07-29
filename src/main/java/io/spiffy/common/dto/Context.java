package io.spiffy.common.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import lombok.Data;

@Data
public class Context {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ModelMap model;
}
