# anim-maven-plugin
Generate gif-animations to documentate multitasking/asynchronuous processes.

# Use in eclipse
To have the animation in eclipse you must edit the javadoc.

```html
<img alt="" src=".test.gif#
   bar at 20x20, size 15x15, fill 0xff0000
">
```
As you can see, the src-attribute is a multi-line attribute. Every line of the src-attribute is a moving widget. The generated .gif-file is in the same folder as the .java-source-file.

