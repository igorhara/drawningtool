package org.igor.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by igorhara on 02/06/2018.
 */
@Getter
@EqualsAndHashCode(exclude = {"color"})
public class Pixel implements Comparable<Pixel> {
    private int x;
    private int y;
    @Setter
    private String color;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
        this.color=" ";
    }

    public Pixel(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public int compareTo(Pixel o) {
        if(o.getY()==this.getY()){
            return Integer.compare(this.getX(),o.getX());
        }
        return Integer.compare(this.getY(),o.getY());
    }

    public boolean hasSameColor(String color){
        return this.color.equals(color);
    }

    public boolean hasSameColor(Pixel other){
        return this.hasSameColor(other.getColor());
    }

    public String getCoordinatesAsString(){
        return "x:"+x+"y:"+y;
    }

}
