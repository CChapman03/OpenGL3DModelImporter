package com.cchapman.importer.utils;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *  This class represents an in game Texture or Image
 */
public class Texture
{
    private BufferedImage img;
    private int id;
    private String path;
    private int width, height;

    /**
     * A constructor to initialize a Texture based an image path
     * @param filename
     */
    public Texture(String filename)
    {
        initBufferedImage(filename);
        id = generateID();
    }

    /**
     * A constructor to initialize a texture based on a builtin java BufferedImage
      * @param image
     */
    public Texture(BufferedImage image)
    {
        this.width = image.getWidth();
        this.height = image.getHeight();

        img = image;

        id = generateID();
    }

    /**
     * A constructor to initialize an empty texture
     * @param width
     * @param height
     * @param isDepth
     */
    public Texture(int width, int height, boolean isDepth)
    {
        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexImage2D(GL_TEXTURE_2D, 0, !isDepth ? GL_RGBA8 : GL_DEPTH_COMPONENT, width, height, 0, !isDepth ? GL_RGBA : GL_DEPTH_COMPONENT, GL_FLOAT, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void initBufferedImage(String Filename)
    {
        path = Filename;

        try
        {
            img = ImageIO.read(new File(Filename));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void release()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int generateID()
    {
        ByteBuffer buff = convertBufferedImage(img);

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buff);


        return textureID;
    }

    public static BufferedImage getColorModelImage(int width, int height)
    {
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{ 8, 8, 8, 8}, true, false,
                Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 4, null);

        texImage = new BufferedImage(glAlphaColorModel, raster, true, new Hashtable<>());

        return texImage;
    }

    public ByteBuffer convertBufferedImage(BufferedImage bufferedImage)
    {
        int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer bb = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB

        for(int y = 0; y < img.getHeight(); y++)
        {
            for(int x = 0; x < img.getWidth(); x++)
            {
                int pixel = pixels[y * img.getWidth() + x];

                bb.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                bb.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                bb.put((byte) (pixel & 0xFF));             // Blue component
                bb.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component. Only for RGBA
            }
        }

        bb.flip();

        return bb;
    }

    public int getId() {
        return id;
    }

    public int getWidth()
    {
        return img.getWidth();
    }

    public int getHeight()
    {
        return img.getHeight();
    }

    public BufferedImage getImageData()
    {
        return this.img;
    }

    public static Texture genAtlasTexture(ArrayList<Texture> textures)
    {
        Texture result = null;

        int sizeX = 0, sizeY = 0;

        for (Texture t : textures)
        {
            sizeX += t.getWidth();
            sizeY = Math.max(sizeY, t.getHeight());
        }

        BufferedImage bi = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        Graphics bi_g = bi.getGraphics();

        int i = 0;
        for (Texture t : textures)
        {
            int dx = i * t.getWidth();
            int dy = 0;
            int dw = t.getWidth();
            int dh = t.getHeight();

            bi_g.drawImage(t.getImageData(), dx, dy, dw, dh, null);

            i++;
        }

        result = new Texture(bi);

        return result;
    }
}
