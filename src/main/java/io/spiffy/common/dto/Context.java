package io.spiffy.common.dto;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

@Data
public class Context {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ModelMap model;
}