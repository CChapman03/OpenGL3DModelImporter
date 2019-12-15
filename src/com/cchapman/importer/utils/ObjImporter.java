package com.cchapman.importer.utils;

import com.cchapman.importer.primitives.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;

public class ObjImporter
{
    public static Mesh importMesh(String filename)
    {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList<>();

        Mesh mesh = new Mesh();

        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new FileReader(new File(filename)));

            Vertex curVertex = new Vertex();
            int faceNum =  0;

            int lineNum = 0;

            String line;
            while((line = br.readLine()) != null)
            {
                String token = line.split(" ")[0];

                if (token.equals("v"))
                {
                    // vertex

                    float vx = Float.valueOf(line.split(" ")[1]);
                    float vy = Float.valueOf(line.split(" ")[2]);
                    float vz = Float.valueOf(line.split(" ")[3]);

                    curVertex.setPosition(new Vector3f(vx, vy, vz));
                }

                if (token.equals("vn"))
                {
                    // normals

                    float vx = Float.valueOf(line.split(" ")[1]);
                    float vy = Float.valueOf(line.split(" ")[2]);
                    float vz = Float.valueOf(line.split(" ")[3]);

                    curVertex.setNormal(new Vector3f(vx, vy, vz));
                }

                if (token.equals("vt"))
                {
                    // texcoords

                    float vx = Float.valueOf(line.split(" ")[1]);
                    float vy = Float.valueOf(line.split(" ")[2]);

                    curVertex.setTexcoord(new Vector2f(vx, vy));
                }

                vertices.add(curVertex);

                if (token.equals("f"))
                {
                    // faces

                    if (faceNum == 0)
                    {
                        // first face
                    }

                    int faceVerts = Integer.valueOf(line.split(" ")[1].split("/").length);

                    int id1 = Integer.valueOf(line.split(" ")[1].split("/")[0]);
                    int id2 = Integer.valueOf(line.split(" ")[1].split("/")[1]);
                    int id3 = Integer.valueOf(line.split(" ")[1].split("/")[2]);
                    int id4 = 0;

                    if (faceVerts == 4)
                    {
                        id4 = Integer.valueOf(line.split(" ")[1].split("/")[3]);
                    }

                    Vertex v0 = vertices.get(id1);
                    Vertex v1 = vertices.get(id2);
                    Vertex v2 = vertices.get(id3);
                    Vertex v3 = null;

                    if (faceVerts == 4)
                    {
                        v3 = vertices.get(id4);
                    }

                    Face curFace = null;

                    if (faceVerts == 3)
                    {
                        curFace = new TriangleFace(v0, v1, v2);
                    }

                    else if (faceVerts == 4)
                    {
                        curFace = new QuadFace(v0, v1, v2, v3);
                    }

                    faces.add(curFace);

                    faceNum++;
                }

                lineNum++;
            }

            mesh.setVertices(vertices);
            mesh.setFaces(faces);

            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return mesh;
    }

    private void importMaterial()
    {

    }
}
