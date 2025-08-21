UiPlayground is a [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) project to experiment with UI APIs, patterns, and tools within the [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform) ecosystem. It contains libraries of UI components/shaders/etc. and a catalog app to demonstrate them on Android, iOS, and desktop.

## Catalog App

![Media Control Sheet demo](docs/assets/app-demo-dark.gif)

[Source](app/src)

## Components

[Source](components/src)

### Media Control Sheet

Media Control Sheet is an interactive component that shows simple media information when collapsed and large-scale media artwork when expanded. It's inspired by similar components in the YouTube and YouTube Music apps.

![Media Control Sheet demo](docs/assets/mediacontrolsheet-demo-dark.gif)

### Media Control Bar

| 0% | 50% | 100% |
| -- | --- | ---- |
| ![Media Control Bar - 0%](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=0.0].png?raw=true) | ![Media Control Bar - 50%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=0.5].png?raw=true>) | ![Media Control Bar - 100%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/components/src/test/snapshots/images/com.alexrdclement.uiplayground.components_MediaControlBarTest_mediaControlBar[progress=1.0].png?raw=true>) |

## Shaders

[Runtime Shaders](https://developer.android.com/reference/android/graphics/RuntimeShader) enable detailed control over how individual pixels are rendered on the screen. Below are shaders I've written, ported, or adapted as [Modifiers](https://developer.android.com/jetpack/compose/modifiers) that can be applied to any Composable that accepts a Modifier.

On Android, these shaders are powered by the RuntimeShader library and the [Android Graphics Shader Language (AGSL)](https://developer.android.com/develop/ui/views/graphics/agsl). On non-Android platforms, the shaders are powered by [Skiko](https://github.com/JetBrains/skiko) and the [Skia Shader Language (SkSL)](https://skia.org/docs/user/sksl/).

[Source](shaders/src)

### Color Split

Color Split allows individual color channels (e.g. red, green, blue) of the target to be shifted by variable amounts across the X and Y axes.

| [0.0, 0.0] | [0.1, 0.0] | [0.2, 0.0] | [0.0, 0.1] | [0.0, 0.2] | [0.2, 0.2] |
| ---------- | ---------- | ---------- | ---------- | ---------- | ---------- |
| ![Color Split 0, 0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.0,_0.0)].png?raw=true>) | ![Color Split 0.1, 0.0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.1,_0.0)].png?raw=true>) | ![Color Split 0.2, 0.0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.2,_0.0)].png?raw=true>) | ![Color Split 0.0, 0.1](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.0,_0.1)].png?raw=true>) | ![Color Split 0.0, 0.2](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.0,_0.2)].png?raw=true>) | ![Color Split 0.2, 0.2](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_whiteCircle[(0.2,_0.2)].png?raw=true>) |
| ![Color Split 0, 0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.0,_0.0)].png?raw=true>) | ![Color Split 0.1, 0.0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.1,_0.0)].png?raw=true>) | ![Color Split 0.2, 0.0](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.2,_0.0)].png?raw=true>) | ![Color Split 0.0, 0.1](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.0,_0.1)].png?raw=true>) | ![Color Split 0.0, 0.2](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.0,_0.2)].png?raw=true>) | ![Color Split 0.2, 0.2](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_ColorSplitTest_blackCircle[(0.2,_0.2)].png?raw=true>) |

### Pixelate

The Pixelate shader assigns the color of all pixels in a region to that of a sample point within the region. Below is the effect with an increasing number of additional pixels in each region.

| 0 | 5 | 10 | 25 | 50 | 100 |
| - | - | -- | -- | -- | --- |
| ![Pixelate - 0 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=0].png?raw=true) | ![Pixelate - 5 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=5].png?raw=true) | ![Pixelate - 10 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=10].png?raw=true) | ![Pixelate - 25 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=25].png?raw=true) | ![Pixelate - 50 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=50].png?raw=true) | ![Pixelate - 100 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_whiteCircle[subdivisions=100].png?raw=true) |
| ![Pixelate - 0 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=0].png?raw=true) | ![Pixelate - 5 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=5].png?raw=true) | ![Pixelate - 10 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=10].png?raw=true) | ![Pixelate - 25 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=25].png?raw=true) | ![Pixelate - 50 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=50].png?raw=true) | ![Pixelate - 100 subdivisions](https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_PixelateTest_blackCircle[subdivisions=100].png?raw=true) |

### Noise

A simple noise effect with increasing levels of opacity.

| 0% | 20% | 50% | 100% |
| -- | --- | --- | ---- |
| ![Noise - 0%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[(0.0,_Monochrome)].png?raw=true>) | ![Noise - 20%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[(0.2,_Monochrome)].png?raw=true>) | ![Noise - 50%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[(0.5,_Monochrome)].png?raw=true>) | ![Noise - 100%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_whiteCircle[(1.0,_Monochrome)].png?raw=true>) |
| ![Noise - 0%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[(0.0,_Monochrome)].png?raw=true>) | ![Noise - 20%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[(0.2,_Monochrome)].png?raw=true>) | ![Noise - 50%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[(0.5,_Monochrome)].png?raw=true>) | ![Noise - 100%](<https://media.githubusercontent.com/media/alexrdclement/UiPlayground/main/shaders/src/test/snapshots/images/com.alexrdclement.uiplayground.shaders_NoiseTest_blackCircle[(1.0,_Monochrome)].png?raw=true>) |

## Screenshot Tests

With the exception of the GIFs, the images above were captured as gold files for automated screenshot tests of the library. Screenshots are generated for each Pull Request and compared against these gold files to ensure UI changes are made intentionally and with review. These screenshot tests are powered by [Paparazzi](https://github.com/cashapp/paparazzi) and [TestParameterInjector](https://github.com/google/TestParameterInjector).

[Component Tests](components/src/androidUnitTest/kotlin/com/alexrdclement/uiplayground/components)

[Shader Tests](shaders/src/androidUnitTest/kotlin/com/alexrdclement/uiplayground/shaders)
