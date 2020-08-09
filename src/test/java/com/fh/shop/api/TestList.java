package com.fh.shop.api;


import java.util.ArrayList;
import java.util.List;

public class TestList {

    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        for(int i = list.size()-1;i>=0;i--){
            list.remove(i);
        }
        System.out.println(list);
    }
}
