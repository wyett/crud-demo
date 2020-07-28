package com.wyett.fastjson;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wyettLei
 * @date : Created in 2020/7/6 10:42
 * @description: basic fastjson usage
 */

public class JSONObjectDemo {
    public static void main(String[] args) {
        List<Dog> doglist = new ArrayList<Dog>();
        doglist.add(new Dog(3, "Tom", "yellow", "Lily"));
        doglist.add(new Dog(3, "Duck", "dark", "Baby"));

        List<String> doglist2 = new ArrayList<>();
        for (Dog d: doglist) {
            String s = JSON.toJSONString(d);
            System.out.println(s);
            doglist2.add(s);
        }

        doglist2.stream().forEach(s -> System.out.println(s));
        doglist2.stream().map(s -> JSON.parseObject(s, Dog.class)).forEach(s -> System.out.println(s));
    }
}
