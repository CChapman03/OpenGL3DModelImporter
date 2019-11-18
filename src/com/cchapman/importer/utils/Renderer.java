package com.cchapman.importer.utils;

import com.cchapman.importer.gameobjects.Camera;
import com.cchapman.importer.primitives.Mesh;
import com.cchapman.importer.primitives.Vertex;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer
{
    private int vbo_id;
    private int ibo_id;
    private FloatBuffer vbo_data;
    private IntBuffer ibo_data;
    private int vertexCount;
    private int indicesCount;
    private boolean drawing;

    private ShaderProgram shader;

    private Camera camera;
    private Mesh mesh;

    private ArrayList<Texture> textures = new ArrayList<>();

    public Renderer()
    {
        ArrayList<ShaderAttribute> attrs = new ArrayList<>();
        attrs.add(new ShaderAttribute(0, "a_position"));
        attrs.add(new ShaderAttribute(1, "a_normal"));
        attrs.add(new ShaderAttribute(2, "a_texcoord"));

        this.shader = new ShaderProgram("src/com/cchapman/importer/shaders/shader.vert.glsl", "src/com/cchapman/importer/shaders/shader.frag", attrs);

        vbo_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);

        ibo_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_id);

        vbo_data = Utils.getEmptyFloatBuffer(48 * 1000000);
        long vbo_size = vbo_data.capacity() * Float.BYTES;
        glBufferData(GL_ARRAY_BUFFER, vbo_size, GL_DYNAMIC_DRAW);

        ibo_data = Utils.getEmptyIntBuffer(48 * 102400);
        long ibo_size = ibo_data.capacity() * Integer.BYTES;
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibo_size, GL_DYNAMIC_DRAW);
    }

    public Renderer(ShaderProgram shader)
    {
        this.shader = shader;

        vbo_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);

        ibo_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_id);

        vbo_data = Utils.getEmptyFloatBuffer(48 * 1000000);
        long size = vbo_data.capacity() * Float.BYTES;
        glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

        ibo_data = Utils.getEmptyIntBuffer(48 * 102400);
        long ibo_size = ibo_data.capacity() * Integer.BYTES;
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibo_size, GL_DYNAMIC_DRAW);
    }

    public void begin(Camera camera)
    {
        this.camera = camera;

        //shader.setUniformMat4("MVP", camera.getMVPMat());

        vertexCount = 0;
        indicesCount = 0;
        drawing = true;
    }

    public void end()
    {
        initShaderUniforms();
        flush();
        drawing = false;
    }

    private void flush()
    {
        vbo_data.flip();
        ibo_data.flip();

		for(Texture i : textures)
			i.bind();

        // Start Shader Rendering
        shader.startRendering();

        // Bind and Upload VBO To GPU
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vbo_data);

        // Bind and Upload IBO To GPU
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_id);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, ibo_data);

        renderVBO();

        // Draw Batch
        //glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glDrawElements(GL_TRIANGLES, ibo_data.capacity(), GL_UNSIGNED_INT, 0);

        // End Shader Rendering
        shader.endRendering();

		for(Texture i : textures)
			i.release();

        // cleanup batch
        vbo_data.clear();
        vertexCount = 0;
    }

    private void renderVBO()
    {
        int stride = Vertex.SIZE * Float.BYTES;

        int positionOffset = 0;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, positionOffset);
        glEnableVertexAttribArray(0);

        int normalOffset = 3 * Float.BYTES;
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, normalOffset);
        glEnableVertexAttribArray(1);

        int texcoordOffset = 6 * Float.BYTES;
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, texcoordOffset);
        glEnableVertexAttribArray(2);

        updateShaderUniforms();
    }

    private void initShaderUniforms()
    {
        // Vertex Shader Uniforms
        shader.setUniformMat4("MVP", camera.getMVPMat());

        // Fragment Shader Uniforms
        shader.setUniform1i("texture", 0);
    }

    private void updateShaderUniforms()
    {
//		shader.setUniform3f("movement", camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ());
//		shader.setUniform3f("rotation", camera.getRotation().getX(), camera.getRotation().getY(), camera.getRotation().getZ());

//		for(int i = 0; i < textures.size(); i++)
//		{
//			shader.setUniform1i("image" + i, i);
//		}
    }

    public void render(Mesh mesh)
    {
        this.mesh = mesh;
        //textures.add(tex);

        vbo_data.put(mesh.getBufferData());
        ibo_data.put(mesh.getIndexBufferData());

        vertexCount += 6;
        indicesCount += 6;
    }

    public void cleanup()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vbo_id);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDeleteBuffers(ibo_id);

        shader.cleanup();
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }

    public void setTextures(ArrayList<Texture> textures) {
        this.textures = textures;
    }
}
