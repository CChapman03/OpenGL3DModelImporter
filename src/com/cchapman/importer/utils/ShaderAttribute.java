package com.cchapman.importer.utils;

public class ShaderAttribute
{
    private int index;
    private String name;

    public ShaderAttribute(String name)
    {
        this(0, name);
    }

    public ShaderAttribute(int index, String name)
    {
        this.index = index;
        this.name = name;
    }

    // Getters and Setters

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
