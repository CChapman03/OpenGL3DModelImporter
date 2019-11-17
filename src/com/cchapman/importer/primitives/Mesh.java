package com.cchapman.importer.primitives;

import java.util.ArrayList;

public class Mesh
{
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Face> faces = new ArrayList<>();

    public float[] getBufferData()
    {
        ArrayList<Float> data = new ArrayList<>();

        for (int i = 0; i < faces.size(); i++)
        {
            Face face = faces.get(i);

            Vertex v0 = vertices.get(face.getIndexBufferData()[0]);
            for (float f : v0.getBufferData())
            {
                data.add(f);
            }

            Vertex v1 = vertices.get(face.getIndexBufferData()[1]);
            for (float f : v1.getBufferData())
            {
                data.add(f);
            }

            Vertex v2 = vertices.get(face.getIndexBufferData()[2]);
            for (float f : v2.getBufferData())
            {
                data.add(f);
            }

            if (face.getClass() == QuadFace.class)
            {
                Vertex v3 = vertices.get(face.getIndexBufferData()[3]);
                for (float f : v3.getBufferData())
                {
                    data.add(f);
                }
            }
        }

        float[] data_result = new float[data.size()];

        int i = 0;
        for (float f : data)
        {
            data_result[i] = f;
            i = (i + 1) % data.size();
        }

        return data_result;
    }

    public int[] getIndexBufferData()
    {
        ArrayList<Integer> data = new ArrayList<>();

        for (int i = 0; i < faces.size(); i++)
        {
            Face face = faces.get(i);

            int[] indices = face.getIndexBufferData();
            data.add(indices[0]);
            data.add(indices[1]);
            data.add(indices[2]);

            if (face.getClass() == QuadFace.class)
            {
                data.add(indices[3]);
            }
        }

        int[] data_result = new int[data.size()];

        int i = 0;
        for (int f : data)
        {
            data_result[i] = f;
            i = (i + 1) % data.size();
        }

        return data_result;
    }
}
