package com.cchapman.importer.primitives;


import java.util.ArrayList;

/**
 * This class represents a 4-sided Face or Quad.
 */
public class QuadFace
{
    private Vertex v0, v1, v2, v3;

    public QuadFace()
    {
    }

    public QuadFace(Vertex v0, Vertex v1, Vertex v2, Vertex v3)
    {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public float[] getBufferData()
    {
        float[] v0_data = v0.getBufferData();
        float[] v1_data = v1.getBufferData();
        float[] v2_data = v2.getBufferData();
        float[] v3_data = v3.getBufferData();

        ArrayList<Float> data = new ArrayList<>();

        for(float f : v0_data) { data.add(f); }
        for(float f : v1_data) { data.add(f); }
        for(float f : v2_data) { data.add(f); }
        for(float f : v3_data) { data.add(f); }

        float[] data_result = new float[data.size()];

        int i = 0;
        for(float f : data) { data_result[i] = f; i = (i + 1) % data.size(); }

        return data_result;
    }

    public int[] getIndexBufferData()
    {
        // Return Indices of Vertices starting at first vertex going Counter-Clockwise
        return new int[] { v0.getIndex(), v3.getIndex(), v2.getIndex(),   // Triangle 0
                           v2.getIndex(), v1.getIndex(), v0.getIndex() }; // Triangle 1
    }

    // Getters and Setters

    public Vertex getV0()
    {
        return v0;
    }

    public void setV0(Vertex v0)
    {
        this.v0 = v0;
    }

    public Vertex getV1()
    {
        return v1;
    }

    public void setV1(Vertex v1)
    {
        this.v1 = v1;
    }

    public Vertex getV2()
    {
        return v2;
    }

    public void setV2(Vertex v2)
    {
        this.v2 = v2;
    }

    public Vertex getV3()
    {
        return v3;
    }

    public void setV3(Vertex v3)
    {
        this.v3 = v3;
    }
}
