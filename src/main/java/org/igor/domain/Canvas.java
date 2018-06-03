package org.igor.domain;

import lombok.Getter;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by igorhara on 02/06/2018.
 */
public class Canvas {
    @Getter
    private Map<Pixel, Pixel> pixels;
    private int xSize;
    private int ySize;


    public Canvas(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.generatePixels();
    }


    private void generatePixels() {
        this.pixels = new HashMap<>(this.xSize * this.ySize);
        IntStream.range(0, this.xSize).forEach(i -> {
            IntStream.range(0, this.ySize).forEach(j -> {
                Pixel pixel = new Pixel(i + 1, j + 1);
                this.pixels.put(pixel, pixel);
            });
        });
    }

    public void fill(int x, int y, String color) {
        Pixel startPixel = this.pixels.get(new Pixel(x, y));
        if (startPixel == null) {
            throwError("Invalid coordinate to start filling: x:" + x + " y:" + y);
        }
        HashSet<Pixel> pixelsToSearch = new HashSet<>();
        pixelsToSearch.add(startPixel);
        HashSet<Pixel> pixelSearched = new HashSet<>();


        while (!pixelsToSearch.isEmpty()) {
            Pixel p = pixelsToSearch.iterator().next();
            pixelSearched.add(p);
            pixelsToSearch.remove(p);
            Set<Pixel> neighbors = this.getNeighbors(p);
            neighbors.removeAll(pixelSearched);
            pixelsToSearch.addAll(neighbors);
            p.setColor(color);
        }

    }

    private Set<Pixel> getNeighbors(Pixel p) {
        Set<Pixel> found = new HashSet<>();
        Optional.ofNullable(getPixelWithSameColor(p.getX() + 1, p.getY(), p.getColor())).ifPresent(found::add);
        Optional.ofNullable(getPixelWithSameColor(p.getX() - 1, p.getY(), p.getColor())).ifPresent(found::add);
        Optional.ofNullable(getPixelWithSameColor(p.getX(), p.getY() + 1, p.getColor())).ifPresent(found::add);
        Optional.ofNullable(getPixelWithSameColor(p.getX(), p.getY() - 1, p.getColor())).ifPresent(found::add);
        return found;
    }

    private Pixel getPixelWithSameColor(int x, int y, String color) {
        return Optional.ofNullable(this.pixels.get(new Pixel(x, y))).filter(p -> p.hasSameColor(color))
                .orElse(null);
    }

    public void createLine(int x1, int y1, int x2, int y2) {
        Pixel p1 = new Pixel(x1, y1, "x");
        Pixel p2 = new Pixel(x2, y2, "x");

        if (!this.pixels.containsKey(p1)) {
            throwError("invalid coordinates for create line " + p1.getCoordinatesAsString());
        }
        if (!this.pixels.containsKey(p2)) {
            throwError("invalid coordinates for create line " + p2.getCoordinatesAsString());
        }
        if (x1 != x2 && y1 != y2) {
            throwError(" only vertical or horizontal lines can be created! p1:" + p1.getCoordinatesAsString()
                    + " p2:" + p2.getCoordinatesAsString());
        }
        String message = "invalid coordinates for building line p1:" + p1.getCoordinatesAsString()
                + " p2:" + p2.getCoordinatesAsString();
        if (x1 == x2) {
            validateCoordinateOrder(y1, y2,message);
            IntStream.rangeClosed(y1, y2).mapToObj(i -> new Pixel(x1, i, "x")).forEach(p -> this.pixels.put(p, p));
        } else {
            validateCoordinateOrder(x1, x2,message);
            IntStream.rangeClosed(x1, x2).mapToObj(i -> new Pixel(i, y1, "x")).forEach(p -> this.pixels.put(p, p));
        }
    }

    private void validateCoordinateOrder(int lower, int higher,String message) {
        if(higher<=lower){
            throwError(message);
        }
    }

    public void createRectangle(int x1, int y1, int x2, int y2){
        createLine(x1,y1,x1,y2);
        createLine(x2,y1,x2,y2);
        createLine(x1,y1,x2,y1);
        createLine(x1,y2,x2,y2);
    }

    private void throwError(String message) {
        throw new RuntimeException(message);
    }

    public String printCanvas(){
        TreeSet<Pixel> orderedPixels= new TreeSet<>(this.getPixels().values());
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1,this.xSize+2).forEach(i->sb.append("-"));
        int y =0;

        for (Pixel p :
                orderedPixels) {
            if(p.getY()!=y){
                if(y!=0){
                    sb.append("|\n|");
                }else {
                    sb.append("\n|");
                }
                y=p.getY();
            }
            sb.append(p.getColor());
        }
        sb.append("|\n");
        IntStream.rangeClosed(1,this.xSize+2).forEach(i->sb.append("-"));

        return sb.toString();
    }
}
