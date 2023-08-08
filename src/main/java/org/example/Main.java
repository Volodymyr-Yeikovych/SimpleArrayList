package org.example;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimpleArrayList<Integer> simpleArrayList = new SimpleArrayList<>();
        simpleArrayList.add(3);
        simpleArrayList.add(2);
        simpleArrayList.add(2); // add
        simpleArrayList.remove(2); // remove
        System.out.println(simpleArrayList.contains(2)); // contains true
        System.out.println(simpleArrayList.contains(4)); // contains false
        System.out.println(simpleArrayList.size()); // size
        System.out.println(simpleArrayList); // toString

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(2);
        System.out.println(arrayList.contains(2));
        System.out.println(arrayList.contains(4));
        System.out.println(arrayList.size());
        System.out.println(arrayList);

        System.out.println("==================");

        System.out.println(simpleArrayList.containsAll(arrayList)); // conatains all true
        arrayList.add(2);
        System.out.println(simpleArrayList.containsAll(arrayList)); // conatains all false

        simpleArrayList.addAll(arrayList); // addAll
        System.out.println(simpleArrayList);

        simpleArrayList.addAll(1, Lists.newArrayList(4,5,6)); // addAll index
        System.out.println(simpleArrayList);

        simpleArrayList.add(2, 9);
        System.out.println(simpleArrayList); // add index

        simpleArrayList.removeAll(arrayList); // removeAll
        System.out.println(simpleArrayList);

        System.out.println(simpleArrayList.get(1)); // get

        simpleArrayList.set(2, 10); // set
        System.out.println(simpleArrayList);

        simpleArrayList.add(10);
        System.out.println(simpleArrayList.indexOf(10)); // indexOf (2)
        System.out.println(simpleArrayList.indexOf(11)); // indexOf (-1)
        System.out.println(simpleArrayList.lastIndexOf(10)); // lastIndexOf (4)
        System.out.println(simpleArrayList);

        List<Integer> subList = simpleArrayList.subList(0, 2); // subList
        System.out.println(subList);

        simpleArrayList.clear(); // clear
        System.out.println(simpleArrayList);

    }
}