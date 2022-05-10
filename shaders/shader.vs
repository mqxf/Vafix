#version 330

layout(location=0) in vec3 aPos;
layout(location=1) in vec4 aColor;
layout(location=2) in vec2 aTexCoords;
layout(location=3) in float aTexID;

out vec2 fTexCoords;
out vec4 fColor;
out float fTexID;

attribute vec3 vertices;

void main(){
	fColor = aColor;
	fTexCoords = aTexCoords;
	fTexID = aTexID;
	gl_Position = vec4(vertices, 1.0);
}