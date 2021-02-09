package com.barryweb.algorithm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author :cjh
 * @date : 10:12 2021/2/7
 */
public class CommonUtil {
    public static List<String> stringsToArraylist(String[] strings){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list,strings);
        return list;
    }
}
