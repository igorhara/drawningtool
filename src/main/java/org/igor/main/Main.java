package org.igor.main;

import org.igor.domain.Canvas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by igorhara on 03/06/2018.
 */
public class Main {

    public static void main(String args[]){
        processFile("input.txt","output.txt");
    }

    public static void processFile(String fileInput,String fileOutput) {
        Canvas c = null;
        try (Scanner scanner = new Scanner(new File(fileInput))) {
            FileWriter fileWriter = new FileWriter(fileOutput);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] commands = line.split(" ");
                c = processCommand(commands,c);
                fileWriter.append(c.printCanvas());
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Canvas processCommand(String[] command,Canvas c){
        if(!"C".equals(command[0]) && c==null){
            throw  new RuntimeException("Cannot draw without a Canvas");
        }
        switch (command[0]){
            case "C":
                c = new Canvas( Integer.valueOf( command[1]), Integer.valueOf(command[2]));
                break;
            case "L":
                c.createLine(Integer.valueOf( command[1]),Integer.valueOf( command[2]),Integer.valueOf( command[3]),
                        Integer.valueOf( command[4]));
                break;
            case "R":
                c.createRectangle(Integer.valueOf( command[1]),Integer.valueOf( command[2]),Integer.valueOf( command[3]),
                        Integer.valueOf( command[4]));
                break;
            case "B":
                c.fill(Integer.valueOf( command[1]), Integer.valueOf(command[2]),command[3]);
                break;
            default:
                throw  new RuntimeException("invalid command "+command[0]);
        }
        return c;
    }


}
