package com.tplentiful.common.utils;

import org.springframework.util.AlternativeJdkIdGenerator;

import java.util.Random;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class RandomUtil {
    public static final AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
    public static final Random RANDOM = new Random();

    public static String getUUID() {
        return alternativeJdkIdGenerator.generateId().toString();
    }

    public static int get(int len) {
        return RandomUtil.RANDOM.nextInt(len - 1);
    }
}
