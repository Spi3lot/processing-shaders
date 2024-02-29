uniform vec2 resolution;
uniform vec3 lookAt;
uniform sampler2D image;

const float pi = 3.14159265359;
const float fov = pi * 2.0;
const float z = 1.0;

vec3 normal(vec3 p) {
	return normalize(p - vec3(0, 0, z));
}

void main() {
	vec2 uv = -1.0 + 2.0 * gl_FragCoord.xy / resolution;
	vec3 p = vec3(uv, z - sqrt(1.0 - dot(uv, uv)));
	vec3 r = -reflect(p, normal(p));
	vec2 st = r.xy * inversesqrt(2.0 * (r.z + 1.0)) / sin(fov / 4.0);
	vec4 color = texture(image, st * 0.5 + 0.5);
	gl_FragColor = color;
}
