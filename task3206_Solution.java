package com.codegym.task.task32.task3206;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* 
Generics for creating a proxy object

*/
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        test(solution.getProxy(Item.class));                        // true false false
        test(solution.getProxy(Item.class, Small.class));           // true false true
        test(solution.getProxy(Item.class, Big.class, Small.class));// true true true
        test(solution.getProxy(Big.class, Small.class));            // true true true, because Big inherits Item
        test(solution.getProxy(Big.class));                         // true true false, because Big inherits Item
        test(solution.getProxy(Small.class));

    }


    private static void test(Object proxy) {
        boolean isItem = proxy instanceof Item;
        boolean isBig = proxy instanceof Big;
        boolean isSmall = proxy instanceof Small;

        System.out.format("%b %b %b\n", isItem, isBig, isSmall);
    }

    private <T> T getProxy(Class<T> out, Class ... classes){

        Set<Class<?>> hastInter = new HashSet<>();

        hastInter.add(out);

        for (int i = 0; i < classes.length; i++) {
            hastInter.add(classes[i]);
            Class<?>[] inters = classes[i].getInterfaces();
            hastInter.addAll(Arrays.asList(inters));
        }

        Class<?>[] interfaces = new Class<?>[hastInter.size()];
        hastInter.toArray(interfaces);

        return out.cast(Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, new ItemInvocationHandler()));

    }
}