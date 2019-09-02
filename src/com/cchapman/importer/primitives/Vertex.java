package com.cchapman.importer.primitives;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * This class represents a Point or Vertex in 3D Space.
 */
public class Vertex
{
    // Vertex Attributes
    private Vector3f position;
    private Vector3f normal;
    private Vector2f texcoord;

    private int index;

    public static int SIZE = 8;

    public Vertex()
    {
        this(new Vector3f(), new Vector3f(), new Vector2f());
    }

    public Vertex(Vector3f position, Vector3f normal, Vector2f texcoord)
    {
        this.position = position;
        this.normal = normal;
        this.texcoord = texcoord;
    }

    public float[] getBufferData()
    {
        float[] data = new float[SIZE];

        data[0] = position.x;
        data[1] = position.y;
        data[2] = position.z;

        data[3] = normal.x;
        data[4] = normal.y;
        data[5] = normal.z;

        data[6] = texcoord.x;
        data[7] = texcoord.y;

        return data;
    }

    // Getters and Setters

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public Vector3f getNormal()
    {
        return normal;
    }

    public void setNormal(Vector3f normal)
    {
        this.normal = normal;
    }

    public Vector2f getTexcoord()
    {
        return texcoord;
    }

    public void setTexcoord(Vector2f texcoord)
    {
        this.texcoord = texcoord;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}
