package com.cchapman.importer.utils;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;
import java.nio.*;
import java.util.ArrayList;

public class Utils
{
    public static FloatBuffer toFloatBuffer(float[] data)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(data.length);
        buff.put(data);
        buff.flip();

        return buff;
    }

    public static IntBuffer toIntBuffer(int[] data)
    {
        IntBuffer buff = BufferUtils.createIntBuffer(data.length);
        buff.put(data);
        buff.flip();

        return buff;
    }

    public static ShortBuffer toShortBuffer(short[] data)
    {
        ShortBuffer buff = BufferUtils.createShortBuffer(data.length);
        buff.put(data);
        buff.flip();

        return buff;
    }

    public static FloatBuffer getEmptyFloatBuffer(int size)
    {
        FloatBuffer fb = BufferUtils.createFloatBuffer(size);

        return fb;
    }

    public static IntBuffer getEmptyIntBuffer(int size)
    {
        IntBuffer ib = BufferUtils.createIntBuffer(size);

        return ib;
    }

    public static ByteBuffer getEmptyByteBuffer(int size)
    {
        ByteBuffer bb = BufferUtils.createByteBuffer(size);

        return bb;
    }

    public static float[] toFloatArray(ArrayList<Float> list)
    {
        float[] array = new float[list.size()];

        for(int i = 0; i < list.size(); i++)
        {
            array[i] = list.get(i);
        }

        return array;
    }

    public static FloatBuffer reserveData(int size)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(size);
        return buff;
    }

    public static DoubleBuffer reserveDoubleData(int size)
    {
        DoubleBuffer buff = BufferUtils.createDoubleBuffer(size);
        return buff;
    }

    public static ShortBuffer reserveShortData(int size)
    {
        ShortBuffer buff = BufferUtils.createShortBuffer(size);
        return buff;
    }

    public static float[] toFloats2f(Vector2f v)
    {
        return new float[]{ v.x, v.y };
    }

    public static float[] toFloats3f(Vector3f v)
    {
        return new float[]{ v.x, v.y, v.z };
    }

    public static float[] toFloats4f(Vector4f v)
    {
        return new float[]{ v.x, v.y, v.z, v.w };
    }

    public static double[] toDoubles(Vector2d v)
    {
        return new double[]{ v.x, v.y };
    }

    public static short[] toShorts(Vector3f v)
    {
        return new short[]{ (short) v.x, (short) v.y, (short) v.z};
    }

    public static short[] hhortList2ShortArray(ArrayList<Short> list)
    {
        short[] s = new short[list.size()];

        for (int i = 0; i < list.size(); i++)
        {
            s[i] = list.get(i);
        }

        return s;
    }

    public static double[] toDoubleArray(float[] floats)
    {
        double[] doubles = new double[floats.length];

        for (int i = 0; i < floats.length; i++)
        {
            doubles[i] = (double)floats[i];
        }

        return doubles;
    }

    public static String loadSource(String path)
    {
        StringBuilder source = new StringBuilder();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            while((line = br.readLine()) != null)
            {
                source.append(line).append('\n');
            }

            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return source.toString();
    }

    public static Vector3f getNormal(Vector3f v)
    {
        float x = (float) (Math.exp(-v.x * v.x / 2) / Math.sqrt(2 * Math.PI));
        float y = (float) (Math.exp(-v.y * v.y / 2) / Math.sqrt(2 * Math.PI));
        float z = (float) (Math.exp(-v.z * v.z / 2) / Math.sqrt(2 * Math.PI));

        Vector3f n = new Vector3f(x, y, z);

        return n;
    }

    public static Matrix4f getIdentityMatrix()
    {
        Matrix4f id = new Matrix4f();

        return id.identity();
    }

    public static Matrix4f getViewMatrix(Vector3f eye, Vector3f lookAt)
    {
        Vector3f Up = new Vector3f(0.0f, 1.0f, 0.0f);

        Matrix4f result = new Matrix4f();
        result.lookAt(eye, lookAt, Up);

        return result;
    }

    public static Matrix4f getFrustumMatrix(float left, float right, float top, float bottom, float near, float far)
    {
        Matrix4f f = new Matrix4f();
        f.frustum(left, right, bottom, top, near, far);

        return f;
    }

    public static Matrix4f getProjectionMatrix(float fovy, float aspect, float near, float far)
    {
        Matrix4f proj = new Matrix4f();
        proj.perspective(fovy, aspect, near, far);

        return proj;
    }

    public static Matrix4f getTranslateMatrix(float x, float y, float z)
    {
        Matrix4f t = new Matrix4f();
        t.translate(x, y, z);

        return t;
    }

    public static Matrix4f getScaleMatrix(float x, float y, float z)
    {
        Matrix4f s = new Matrix4f();
        s.scale(x, y, z);

        return s;
    }

    public static Matrix4f getRotationMatrix(float angle, float x, float y, float z)
    {
        Matrix4f r = new Matrix4f();
        r.rotate(angle, x, y, z);

        return r;
    }

    public static Matrix4f getNormalMatrix()
    {
        Matrix4f normal = new Matrix4f();
        normal.normal();

        return normal;
    }

    public static Node getNode(String tagName, NodeList list)
    {
        for(int i = 0; i < list.getLength(); i++)
        {
            Node node = list.item(i);

            if(node.getNodeName().equalsIgnoreCase(tagName))
            {
                return node;
            }
        }

        return null;
    }

    public static String getNodeValue(Node node)
    {
        NodeList childNodes = node.getChildNodes();

        for(int i = 0; i < childNodes.getLength(); i++)
        {
            Node nodedata = childNodes.item(i);

            if(nodedata.getNodeType() == Node.TEXT_NODE)
            {
                return nodedata.getNodeValue();
            }
        }

        return "";
    }

    public static String getNodeValue(String tagName, NodeList nodes)
    {
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);

            if(node.getNodeName().equalsIgnoreCase(tagName))
            {
                NodeList childNodes = node.getChildNodes();

                for(int j = 0; j < childNodes.getLength(); j++)
                {
                    Node nodeData = childNodes.item(j);

                    if(nodeData.getNodeType() == Node.TEXT_NODE)
                    {
                        return nodeData.getTextContent();
                    }
                }
            }
        }

        return "";
    }

    public static String getNodeAttr(String attrName, Node node)
    {
        NamedNodeMap attrs = node.getAttributes();

        for(int i = 0; i < attrs.getLength(); i++)
        {
            Node attr = attrs.item(i);

            if(attr.getNodeName().equalsIgnoreCase(attrName))
            {
                return attr.getNodeValue();
            }
        }

        return "";
    }

    public static String getNodeAttr(String tagName, String attrName, NodeList nodes)
    {
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);

            if(node.getNodeName().equalsIgnoreCase(tagName))
            {
                NodeList childNodes = node.getChildNodes();

                for(int j = 0; j < childNodes.getLength(); j++)
                {
                    Node nodeData = childNodes.item(j);

                    if(nodeData.getNodeType() == Node.ATTRIBUTE_NODE)
                    {
                        if(nodeData.getNodeName().equalsIgnoreCase(attrName))
                        {
                            return nodeData.getNodeValue();
                        }
                    }
                }
            }
        }

        return "";
    }

    public static String getNodeData(String tagName, Node parentNode)
    {
        NodeList childNodes = parentNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node data = childNodes.item(i);
            Element data_Element = (Element) data;

            NodeList list = data_Element.getElementsByTagName(tagName);

            for (int j = 0; j < list.getLength(); j++)
            {
                Node n = list.item(j);

                return n.getNodeValue();
            }
        }

        return "";
    }

    public static void printBuffer(FloatBuffer data)
    {
        for (int i = 0; i < data.capacity(); i++)
        {
            System.out.print(data.get(i) + ",");
        }

        System.out.print("\n");
    }

    public static void printBuffer(ShortBuffer data)
    {
        for (int i = 0; i < data.capacity(); i++)
        {
            System.out.print(data.get(i) + ",");
        }

        System.out.print("\n");
    }
}
