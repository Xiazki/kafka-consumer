package com.xiazki.kafka.executor;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        List<te<String>> lit = new ArrayList<>();

        te<String> te = new te<>();
        te.setData("testaaa");

        holder holder = new holder();
        holder.setT(te);
        test(holder.getT());
    }

    public static <T> void test(te<T> tes) {

        System.out.println(tes.getData());

    }

}

class  holder{

    te<?> t;

    public te<?> getT() {
        return t;
    }

    public void setT(te<?> t) {
        this.t = t;
    }
}

class te<T> {
    T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
