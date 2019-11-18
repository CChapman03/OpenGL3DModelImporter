package com.cchapman.importer.gameobjects;

import com.cchapman.importer.main.MainWindow;
import com.cchapman.importer.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera
{
    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f target;
    private float fov, near, far;

    public Camera()
    {

    }

    public Camera(Vector3f position, Vector3f target)
    {
        this(position, target, 70.0f, 0.1f, 1000.0f);
    }

    public Camera(Vector3f position, Vector3f target, float fov, float near, float far)
    {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    public Matrix4f getMVPMat()
    {
        return Utils.getProjectionMatrix(this.fov, (float)MainWindow.getWindow_width() / (float)MainWindow.getWindow_height(), near, far).mul(Utils.getViewMatrix(this.position, this.target));
    }
}
