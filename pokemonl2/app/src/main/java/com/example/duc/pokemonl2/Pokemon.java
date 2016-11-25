package com.example.duc.pokemonl2;

/**
 * Created by DUC on 20/11/2016.
 */

public class Pokemon {
    private int id;
    private String name;
    private String tag;
    private int gen;
    private String img;
    private String color;

    public Pokemon(int id, String name, String tag, int gen, String img, String color) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.gen = gen;
        this.img = img;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getGen() {
        return gen;
    }

    public String getImg() {
        return img;
    }

    public String getColor() {
        return color;
    }

    public Pokemon(String name, String tag, int gen, String img, String color){
        this(-1, name,tag,gen,img,color);
    }

    public void setId(int id) {
        this.id = id;
    }
}
