package com.blackoutburst.workshop.utils;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {
    public static List<List<String>> transpose2dStringList(List<List<String>> startList) {
        List<List<String>> transposedList = new ArrayList<>();

        int max_size = 0;

        for (List<String> subList : startList) {
            if (subList.size() > max_size) { max_size =  subList.size(); }
        }

        for (int i = 0; i < max_size; i++) {
            List<String> tempList = new ArrayList<>();

            for (List<String> subList : startList) {
                if (subList.size() > i) {
                    tempList.add(subList.get(i));
                }
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }
}
