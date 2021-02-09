package com.barryweb.algorithm.leedcode;

import com.barryweb.algorithm.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :cjh
 * @date : 9:55 2021/2/7
 */
public class Min1 {
    public static void main(String[] args) {
        Min1 min1 = new Min1();
        String[] string = {"hot","dot","dog","lot","log","cog"};
        List<String> wordlist = CommonUtil.stringsToArraylist(string);
        String beginword="hit";
        String endword="cog";
        min1.ladderLength(beginword,endword,wordlist);
    }
    //逐一跑路径  、、但是东西太多怎么办
    //字母表
    //路径图
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        return 0;
    }
}
