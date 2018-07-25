/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;

/**
 *
 * @author filippotessaro
 */
public class Map {
    
    private Scanner m;
    
    private String Map[] = new String[14];
    
    private Image grass,wall, finish;
    
    public Map(){
        ImageIcon img = new ImageIcon("risorse/images/grass.png");
        grass = img.getImage();
        
        img = new ImageIcon("risorse/images/wall.png");
        wall = img.getImage();
        img = new ImageIcon("risorse/images/finish.png");
        finish = img.getImage();
        openFile();
        readFile();
        closeFile();
    }
    
    public Map(int livello){
        ImageIcon img = new ImageIcon("risorse/images/grass.png");
        grass = img.getImage();
        
        img = new ImageIcon("risorse/images/wall.png");
        wall = img.getImage();
        img = new ImageIcon("risorse/images/finish.png");
        finish = img.getImage();
//        openFile();
        openFileTxt(livello);
        readFile();
        closeFile();
    }
    
    public Image getGrass(){
        return grass;
    }
    public Image getWall(){
        return wall;
    }
    
    public Image getFinish(){
        return finish;
    }
    
    public String getMap(int x, int y){
        String index = Map[y].substring(x,x+1);
        return index;
    }
    
    public void openFile(){
        try{
        	String name = "risorse/levels/Map.txt";
        m = new Scanner(new File(name));}
        catch(Exception e){
            System.out.println("error loading map");
        }
    }
    
    public void openFileTxt(int livello){
        try{
        	String name = "risorse/levels/";
        	String txt = ".txt";
//        	String nameFile = name + String.valueOf(livello) + txt ;
        	String nameFile = name + String.valueOf(livello) + txt ;
        m = new Scanner(new File(nameFile));}
        catch(Exception e){
            System.out.println("error loading map");
        }
    }
    
    public void readFile(){
        while(m.hasNext()){
            for(int i=0; i<14; i++){
                Map[i] = m.next();
            }
        }
    }
    
    public void closeFile(){
        m.close();
    }
}
