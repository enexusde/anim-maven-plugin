# anim-maven-plugin
Generate gif-animations to documentate multitasking/asynchronuous processes.

# Use in eclipse
To have the animation in eclipse you must edit the javadoc.

Inside the `/** */`-part of the javadoc HTML is allowed.

To add animation to javadoc you must edit the HTML in the javadoc.

```html
<pre>
<img alt="" src="./doc-files/test.gif#
   bar at 20x20, size 15x15, fill 0xff0000
">
</pre>
```
As you can see, the src-attribute is a multi-line attribute. Every line of the src-attribute is a moving widget. The generated .gif-file is in the same folder as the .java-source-file.

| Question                            | Answer                                                                                    | Ref  |
| ----------------------------------- | ----------------------------------------------------------------------------------------- | ---- |
| Is the gif part of the .jar?        | Depends! If the .gif is in the sub-directory named `doc-files` it is not part of the .jar | QA01 |
| Is the formatter working correctly? | Depends! You must surround the `<img` by the `<pre>` tag.                                 | QA02 |
| Is `doc-files` a package?           | No, its filtered from the java package structure                                          | QA03 |
