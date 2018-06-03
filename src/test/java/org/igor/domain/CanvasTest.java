package org.igor.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by igorhara on 03/06/2018.
 */
public class CanvasTest {

    Canvas canvas;

    @Before
    public void setUp(){
        canvas = new Canvas(5,5);

    }

    @Test
    public void fillEntireCanvas() throws Exception {
        canvas.fill(3,3,"c");
        Assert.assertTrue(canvas.getPixels().values().stream().allMatch(p->p.hasSameColor("c")));
    }

    @Test
    public void fillWithRectangle() throws Exception {
        Set<Pixel> pixelForVerificationRectangle = getPixelsForTest(new int[][]{
                {1,1},{1,2},{1,3},{1,4},{1,5},
                {2,1},                  {2,5},
                {3,1},{3,2},{3,3},{3,4},{3,5}});
        Set<Pixel> pixelForVerificationFill = getPixelsForTest(new int[][]{
                {2,2},{2,3},{2,4}});
        Set<Pixel> pixelForVerificationOther = getPixelsForTest(new int[][]{
                {4,1},{4,2},{4,3},{4,4},{4,5},
                {5,1},{5,2},{5,3},{5,4},{5,5}});

        canvas.createRectangle(1,1,3,5);
        canvas.fill(2,2,"c");
        Map<Pixel, Pixel> pixels = canvas.getPixels();
        Assert.assertTrue(pixelForVerificationRectangle.stream().allMatch(p-> pixels.get(p).hasSameColor("x")));
        Assert.assertTrue(pixelForVerificationFill.stream().allMatch(p-> pixels.get(p).hasSameColor("c")));
        Assert.assertTrue(pixelForVerificationOther.stream().allMatch(p-> pixels.get(p).hasSameColor(" ")));

    }

    @Test
    public void createLine_vertical() throws Exception {
        Set<Pixel> pixelForVerification = getPixelsForTest(new int[][]{{1,1},{2,1},{3,1},{4,1},{5,1}});
        canvas.createLine(1,1,5,1);
        executeVerification(pixelForVerification);
    }
    @Test
    public void createLine_horizontal() throws Exception {
        Set<Pixel> pixelForVerification = getPixelsForTest(new int[][]{{1,1},{1,2},{1,3},{1,4},{1,5}});
        canvas.createLine(1,1,1,5);
        executeVerification(pixelForVerification);
    }

    @Test(expected = RuntimeException.class)
    public void createLine_invalidCoordinates_same_pixel() throws Exception {
        canvas.createLine(1,1,1,1);
    }
    @Test(expected = RuntimeException.class)
    public void createLine_invalidCoordinates_allDifferent() throws Exception {
        canvas.createLine(1,1,2,2);
    }

    private void executeVerification(Set<Pixel> pixelForVerification) {
        Map<Pixel, Pixel> pixels = canvas.getPixels();
        Assert.assertTrue(pixelForVerification.stream().allMatch(p-> pixels.get(p).hasSameColor("x")));
        pixels.keySet().removeAll(pixelForVerification);
        Assert.assertTrue(pixels.values().stream().allMatch(p-> p.hasSameColor(" ")));
    }

    @Test
    public void createRectangle() throws Exception {
        Set<Pixel> pixelForVerification = getPixelsForTest(new int[][]{
                {1,1},{1,2},{1,3},{1,4},{1,5},
                {2,1},                  {2,5},
                {3,1},{3,2},{3,3},{3,4},{3,5}});
        canvas.createRectangle(1,1,3,5);
        executeVerification(pixelForVerification);
    }
    @Test
    public void printCanvas_withLine(){
        String printResult =
                "-------\n"+
                "| x   |\n"+
                "| x   |\n"+
                "| x   |\n"+
                "| x   |\n"+
                "|     |\n"+
                "-------";
        canvas.createLine(2,1,2,4);
        Assert.assertEquals(printResult,canvas.printCanvas());
    }

    private Set<Pixel> getPixelsForTest(int[][] coordinates){
        return Arrays.stream(coordinates).map(c->new Pixel(c[0],c[1])).collect(Collectors.toSet());
    }

}