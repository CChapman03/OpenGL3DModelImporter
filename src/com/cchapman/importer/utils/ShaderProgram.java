package com.cchapman.importer.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class ShaderProgram
{
    private int p_id, vs_id, fs_id;
    private String vs_src, fs_src;

    public ShaderProgram(String vs_src, String fs_src, ArrayList<ShaderAttribute> attributes)
    {
        this.vs_src = vs_src;
        this.fs_src = fs_src;

        vs_id = createShader(vs_src, GL20.GL_VERTEX_SHADER);
        fs_id = createShader(fs_src, GL20.GL_FRAGMENT_SHADER);
        p_id = GL20.glCreateProgram();

        GL20.glAttachShader(p_id, vs_id);
        GL20.glAttachShader(p_id, fs_id);

        bindAttributes(attributes);

        GL20.glLinkProgram(p_id);
        GL20.glValidateProgram(p_id);
    }

    public void startRendering()
    {
        GL20.glUseProgram(p_id);
    }

    public void endRendering()
    {
        GL20.glUseProgram(0);
    }

    public void cleanup()
    {
        endRendering();
        GL20.glDetachShader(p_id, vs_id);
        GL20.glDetachShader(p_id, fs_id);
        GL20.glDeleteShader(vs_id);
        GL20.glDeleteShader(fs_id);
        GL20.glDeleteProgram(p_id);
    }

    public void bindAttributes(ArrayList<ShaderAttribute> attributes)
    {
        for(ShaderAttribute attr : attributes)
        {
            bindAttribute(attr.getName());
        }
    }

    public void bindAttribute(String varName)
    {
        int idx = GL20.glGetAttribLocation(p_id, varName);

        GL20.glBindAttribLocation(p_id, idx, varName);
    }

    public void bindAttribute(int index, String varName)
    {
        GL20.glBindAttribLocation(p_id, index, varName);
    }

    private int createShader(String src, int type)
    {
        String shader_src = Utils.loadSource(src);

        int shader_id = GL20.glCreateShader(type);
        GL20.glShaderSource(shader_id, shader_src);
        GL20.glCompileShader(shader_id);

        if(GL20.glGetShaderi(shader_id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Error: Could not compile " + ((type == GL20.GL_VERTEX_SHADER) ? "vertex" : "fragment") + " shader!");
            System.out.println(GL20.glGetShaderInfoLog(shader_id, 500));
        }

        return shader_id;
    }

    public int getAttributeIndex(String name)
    {
        return GL20.glGetAttribLocation(p_id, name);
    }

    public int getUniformLocation(String name)
    {
        return GL20.glGetUniformLocation(p_id, name);
    }

    public void setUniform1i(String name, int v0)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform1i(loc, v0);
    }

    public void setUniform1f(String name, float v0)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform1f(loc, v0);
    }

    public void setUniform2f(String name, float v0, float v1)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform2f(loc, v0, v1);
    }
    public void setUniform3f(String name, float v0, float v1, float v2)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform3f(loc, v0, v1, v2);
    }
    public void setUniform3f(String name, Vector3f vec)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform3f(loc, vec.x, vec.y, vec.z);
    }
    public void setUniform4f(String name, float v0, float v1, float v2, float v3)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);
        GL20.glUniform4f(loc, v0, v1, v2, v3);
    }

    public void setUniformMat4(String name, Matrix4f mat)
    {
        int loc = GL20.glGetUniformLocation(p_id, name);

        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        Matrix4f m = mat;
        m.get(fb);

        GL20.glUniformMatrix4fv(loc, false, fb);
    }
}
