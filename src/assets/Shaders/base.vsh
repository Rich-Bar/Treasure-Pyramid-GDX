varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
	v_color = gl_Color;
	v_texCoord0 = gl_MultiTexCoord0;
}