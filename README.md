

## Components

[Source](components/src/main/kotlin/com/alexrdclement/uiplayground/components/)

### Media Control Bar

| 0% | 50% | 100% |
| -- | --- | ---- |
| ![Media Control Bar - 0%](<components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=0.0].png>) | ![Media Control Bar - 50%](<components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=0.5].png>) | ![Media Control Bar - 100%](<components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=1.0].png>) |

### Media Control Sheet

![Media Control Sheet demo](docs/assets/mediacontrolsheet-demo-dark.gif)

## Shaders

[Source](shaders/src/main/kotlin/com/alexrdclement/uiplayground/shaders/)

### Chromatic Abberration

| [0.0, 0.0] | [0.1, 0.0] | [0.2, 0.0] | [0.0, 0.1] | [0.0, 0.2] | [0.2, 0.2] |
| ---------- | ---------- | ---------- | ---------- | ---------- | ---------- |
| ![Chromatic Aberration 0, 0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.0, 0.0)].png>) | ![Chromatic Aberration 0.1, 0.0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.1, 0.0)].png>) | ![Chromatic Aberration 0.2, 0.0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.2, 0.0)].png>) | ![Chromatic Aberration 0.0, 0.1](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.0, 0.1)].png>) | ![Chromatic Aberration 0.0, 0.2](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.0, 0.2)].png>) | ![Chromatic Aberration 0.2, 0.2](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_whiteCircle[(0.2, 0.2)].png>) |
| ![Chromatic Aberration 0, 0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.0, 0.0)].png>) | ![Chromatic Aberration 0.1, 0.0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.1, 0.0)].png>) | ![Chromatic Aberration 0.2, 0.0](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.2, 0.0)].png>) | ![Chromatic Aberration 0.0, 0.1](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.0, 0.1)].png>) | ![Chromatic Aberration 0.0, 0.2](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.0, 0.2)].png>) | ![Chromatic Aberration 0.2, 0.2](<shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ChromaticAberrationTest_blackCircle[(0.2, 0.2)].png>) |

### Pixellate

| 0 | 5 | 10 | 25 | 50 | 100 |
| - | - | -- | -- | -- | --- |
| ![Pixellate - 0 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[0].png) | ![Pixellate - 5 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[5].png) | ![Pixellate - 10 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[10].png) | ![Pixellate - 25 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[25].png) | ![Pixellate - 50 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[50].png) | ![Pixellate - 100 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[100].png) |
| ![Pixellate - 0 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[0].png) | ![Pixellate - 5 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[5].png) | ![Pixellate - 10 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[10].png) | ![Pixellate - 25 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[25].png) | ![Pixellate - 50 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[50].png) | ![Pixellate - 100 subdivisions](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[100].png) |

### Noise

| 0% | 20% | 50% | 100% |
| -- | --- | --- | ---- |
| ![Noise - 0%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[0.0].png) | ![Noise - 20%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[0.2].png) | ![Noise - 50%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[0.5].png) | ![Noise - 100%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[1.0].png) |
| ![Noise - 0%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[0.0].png) | ![Noise - 20%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[0.2].png) | ![Noise - 50%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[0.5].png) | ![Noise - 100%](shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[1.0].png)

## Screenshot tests

[Components](components\src\test)

[Shaders](shaders\src\test)