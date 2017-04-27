[![Download](https://api.bintray.com/packages/falnatsheh/maven/MarkdownView/images/download.svg)](https://bintray.com/falnatsheh/maven/MarkdownView/_latestVersion)
![MarkdownView screenshot](https://cloud.githubusercontent.com/assets/13031505/25467459/477e1e28-2b49-11e7-94d7-291346027a22.gif)

## About

This is a lightweight support library for [`MarkdownView`](https://github.com/falnatsheh/MarkdownView). `MarkdownView` is a Markdown renderer for Android. It has backward compatibility with API 7 and higher. For more information, see the [original project](https://github.com/falnatsheh/MarkdownView).

**NOTE**: Some unimportant features of `MarkdownView` are removed in order to make the library lighter.

## Getting started

- To add MarkdownView to your project, add the following to `build.gradle` file:
```javascript
	dependencies { 
	    compile 'us.feras.mdv:markdownview:1.1.0'
	}
```

## Usage

Add MarkdownView to your layout: 

```xml
    <us.feras.mdv.MarkdownView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/markdownView" />
```

and reference it in your Activity/Fragment:  

```java
MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
markdownView.loadMarkdown("## Hello Markdown"); 
```
**Note**:
You could also create the view by code. Below an example of how to set the whole activity to be a MarkdownView by Adding the following to your onCreate method:

```java
  MarkdownView markdownView = new MarkdownView(this);
  setContentView(markdownView);
  markdownView.loadMarkdown("## Hello Markdown"); 
```

## Demo App and Code Sample

The above screenshots taking from the demo app which could be found here. The demo app include code to demonstrate: 

- Loading Local Markdown File. 
- Loading Remote Markdown File. 
- Loading Markdown text.
- Live Preview sample code (similar to [Marked Mac app](http://marked2app.com/))
- Themes

## Loading Markdown text or file: 

- `loadMarkdown(String text)`:
Using this method will result in loading md string to the MarkdownView and displaying it as HTML. 

 
- `loadMarkdownFile(String url)`:
You can use this method to load local or online files. 

To load a local file, you have to add it to your assets folder and pass a url that start with "file:///android_asset/" : 

```java
markdownView.loadMarkdownFile("file:///android_asset/myFile.md");
```

To load a remote file you need to pass the full url :    

```java
markdownView.loadMarkdownFile("http://www.my-site.com/myFile.md");
```

## Theming

You could apply custom CSS to the MarkdownView. Example: 

```java
markdownView.loadMarkdownFile("file:///android_asset/hello.md","file:///android_asset/MyCustomTheme.css");
```
You could take a look at CSS example [here](https://github.com/falnatsheh/MarkdownView/tree/master/MarkdownViewDemo/assets/markdown_css_themes), you could also view them in the sample app.

## ChangeLog: 

- **MarkdownView 1.1.0**:
	- Support Loading Markdown file from assets subfolders (Thanks [@echodjb](https://github.com/DiegoRosado)). 
- **MarkdownView 1.0.0**:
	- Convert to Gradle Project (Avillable now on [jCenter](https://bintray.com/falnatsheh/maven/MarkdownView/view)). 
	- Fix CSS Issue (Thanks [@swanson](https://github.com/swanson) & [@echodjb](https://github.com/echodjb)). 
	- Update demo app.  

						
## License
```
Copyright for portions of project MarkdownViewSupport are held by Feras Alnatsheh, 2011 as part of project MarkdownView. All other copyright for project MarkdownViewSupport are held by Minsuk Eom, 2017.

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
