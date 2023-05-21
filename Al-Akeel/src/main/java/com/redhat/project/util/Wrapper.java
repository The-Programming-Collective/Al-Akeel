package com.redhat.project.util;

import java.io.Serializable;

public class Wrapper<T, U> implements Serializable{
    public T value1;
    public U value2;

    public Wrapper(){}

    public Wrapper(T value1, U value2){
        this.value1 = value1;
        this.value2 = value2;
    }
    
}
