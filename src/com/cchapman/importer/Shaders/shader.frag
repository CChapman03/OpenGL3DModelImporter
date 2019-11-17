#version 430 core

in vec3 v_position;
in vec3 v_normal;
in vec2 v_texcoord;

out vec4 out_color;

uniform sampler2D texture;

void main()
{
    out_color = vec4(v_normal.xyz, 1.0);
}
