package com.vikko.demo.algorithm.year2021.month4;

import java.util.Arrays;

public class ReverseString {

    public static void main(String[] args) {
        char[] chars = "hello world".toCharArray();
        reverseString(chars);
        System.out.println(Arrays.toString(chars));
    }

    public static void reverseString(char[] s) {
        int n = s.length;
        for (int left = 0, right = n - 1; left < right; ++left, --right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
    }
}