package com.cchapman.importer.primitives;

/***
 *  This class repesents a Line or Edge of a face that contains 2 vertices.
 */
public class Edge
{
    private Vertex v0, v1;

    public Edge()
    {

    }

    public Edge(Vertex v0, Vertex v1)
    {
        this.v0 = v0;
        this.v1 = v1;
    }

    public float getLength()
    {
        return v0.getPosition().distance(v1.getPosition());
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
}
