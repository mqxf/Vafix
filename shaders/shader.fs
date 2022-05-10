#version 330

uniform float r;
uniform float g;
uniform float b;
uniform float a;

uniform sampler2D uTextures[8];

in vec2 fTexCoords;
in vec4 fColor;
in float fTexID;

out vec4 color;

void main(){
	if(fTexID > 0){
		int id = int(fTexID);
		color = fColor * texture(uTextures[id], fTexCoords);
	}else{
		color = fColor;
	}
}
