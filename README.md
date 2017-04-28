# MarkdownViewSupport

[![Download](https://api.bintray.com/packages/boxresin/maven/MarkdownViewSupport/images/download.svg) ](https://bintray.com/boxresin/maven/MarkdownViewSupport/_latestVersion)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/BoxResin/MarkdownViewSupport/master/LICENSE.txt)

![MarkdownView screenshot](https://cloud.githubusercontent.com/assets/13031505/25468471/09454dd6-2b51-11e7-91ad-dd3e02f27359.gif)

## About

This is a lightweight support library for [`MarkdownView`](https://github.com/falnatsheh/MarkdownView). `MarkdownView` is a Markdown renderer for Android. It has backward compatibility with API 7 and higher. For more information, see the [original project](https://github.com/falnatsheh/MarkdownView).

**NOTE**: Some unimportant features of `MarkdownView` have been removed in order to make the library lighter.

## Getting started

To add `MarkdownViewSupport` library to your project, add the following to `build.gradle` file:
```gradle
dependencies { 
    compile 'boxresin.library:MarkdownViewSupport:1.0.0'
}
```

## Usage

Add `<MarkdownView/>` to your layout: 

```xml
<us.feras.mdv.MarkdownView
    xmlns:mdv="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    mdv:cssFromAssets="markdown_css_themes/paperwhite.css"
    mdv:markdown="## Hello Markdown"
    android:id="@+id/markdownView" />
```
`cssFromAssets` attribute is optional. If you want to apply custom style to the Markdown text, you should make a CSS file at `assets` directory and attach its path to `cssFromAssets` attribute. You can also use `markdownFromAssets` attribute to set Markdown text from the `assets` as well.

and refer it in your Activity/Fragment:

```java
MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
```
**NOTE**:
You could also create the view by code. Below an example of how to set the whole activity to be a MarkdownView by Adding the following to your onCreate method:

```java
MarkdownView markdownView = new MarkdownView(this);
setContentView(markdownView);

markdownView.loadMarkdown("## Hello Markdown");
markdownView.loadCssFromAssets("markdown_css_themes/paperwhite.css");
```

For more information, see [Whole XML attributes and methods of MarkdownView](https://github.com/BoxResin/MarkdownViewSupport/wiki/Whole-XML-attributes-and-methods-of-MarkdownView).

## Theming

You could take a look at CSS example [here](https://github.com/BoxResin/MarkdownViewSupport/wiki/Markdown-CSS-form), you could also view them in the sample app.

## License
```
Copyright 2017 Minsuk Eom
Copyright 2011 Feras Alnatsheh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
