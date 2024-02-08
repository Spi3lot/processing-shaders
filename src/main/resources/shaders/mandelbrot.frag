uniform vec2 resolution;
uniform vec2 bottomLeft;
uniform vec2 topRight;
uniform int iterationCount;

vec2 cmul(vec2 a, vec2 b) {
    return mat2(a.x, a.y, -a.y, a.x) * b;
}

int iterate(vec2 c) {
    vec2 z = c;

    for (int i = 0; i < iterationCount; i++) {
        z = cmul(z, z) + c;

        if (dot(z, z) > 4.0) {
            return i;
        }
    }

    return iterationCount;
}

void main() {
    vec2 c = mix(bottomLeft, topRight, gl_FragCoord.xy / resolution.y);
    int iterations = iterate(c);
    float color = float(iterations) / float(iterationCount);
    gl_FragColor = vec4(vec3(color), 1.0);
}
