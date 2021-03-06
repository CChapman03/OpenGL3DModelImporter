package com.cchapman.importer.main;

import com.cchapman.importer.gameobjects.Camera;
import com.cchapman.importer.primitives.Mesh;
import com.cchapman.importer.utils.ObjImporter;
import com.cchapman.importer.utils.Renderer;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.swing.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 *  This class represents the main window that will show upon running the application
 */
public class MainWindow
{
    // The window handle
    private long window;

    private static int window_width;
    private static int window_height;

    private String OBJFile = null;
    private Mesh OBJMesh = null;

    private void init()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        OBJFile = chooser.getSelectedFile().getAbsolutePath();
        OBJMesh = ObjImporter.importMesh(OBJFile);

        chooser = null;

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        chooser = null;

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(1280, 720, "Hello World!", NULL, NULL);
        if (window == NULL)
        {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush())
        {
            // Set the window size to 720p
            glfwSetWindowSize(window, 1280, 720);

            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            this.window_width = pWidth.get(0);
            this.window_height = pHeight.get(0);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void run()
    {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            Camera camera = new Camera(new Vector3f(0f, 0f, -12f), new Vector3f(0,0,0));

            Renderer meshRenderer = new Renderer();
            meshRenderer.begin(camera);
            meshRenderer.render(OBJMesh);
            meshRenderer.end();
        }
    }

    public static void main(String[] args)
    {
        new MainWindow().run();
    }

    public static int getWindow_width()
    {
        return window_width;
    }

    public void setWindow_width(int window_width)
    {
        this.window_width = window_width;
    }

    public static int getWindow_height()
    {
        return window_height;
    }

    public void setWindow_height(int window_height)
    {
        this.window_height = window_height;
    }
}
