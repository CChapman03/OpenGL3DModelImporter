#version 430 core

in vec3 a_position;
in vec3 a_normal;
in vec2 a_texcoord;

out vec3 v_position;
out vec3 v_normal;
out vec2 v_texcoord;

uniform mat4 MVP;

void main()
{
    v_position = a_position;
    v_normal = a_normal;
    v_texcoord = a_texcoord;

    gl_Position = MVP * vec4(a_position, 1.0);
}
