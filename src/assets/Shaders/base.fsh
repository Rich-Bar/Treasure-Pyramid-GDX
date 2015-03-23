varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
	gl_FragColor = vec4(v_color.rgb(), 1/gl_Position.x);
}