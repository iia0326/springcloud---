package com.kangyi.util;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class BannerBuilder implements Banner {

    private static final String BANNER = "    便 民 战 役  —— 疫  情 排 查 服 务 平 台     ";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(BANNER);
    }
}
