# Protofy

Protofy is a WYSIWYG UI creator for Android that allows developers to design draft layouts for their apps and have it exported into XML. Protofy can be used offline and is intended for developers and designers to create high level mock layouts that can quickly be implemented into their projects for further editing. 

You can check out the original Protofy listing on the [Amazon app store](https://www.amazon.com/Protofy/dp/B016JY9NV2/ref=sr_1_1?s=mobile-apps&ie=UTF8&qid=1506921173&sr=1-1&keywords=Protofy) or on [Google Play](https://play.google.com/store/apps/details?id=com.Protofy.protofy).


### Intro Screenshot
![](https://lh3.googleusercontent.com/_0jko0l4GqVbmVZDD1Y9NQQXcoZuufok2pnfYg1LxR_XuxjnH3Qxb-ekKOvLimoNNVQ=h900-rw)

## Getting Started

To get started, you will need a copy of the source code which you can either download the zip or clone with git using:
```
git clone https://github.com/DecoderMG/Protofy.git
```

You will also need to ensure you have the compatible Android SDKs installed on your machine. Out of the box, Protofy will use the following:

* Minimum supported Android SDK - 16
* Target SDK - 23
* Compile SDK - 26 

Protofy also utilizes numerous third-party libraries for its user interface elements. Some third-party libraries are included in the source however, libraries like MaterialList are included via gradle. These are likely to change and may require an update to work with Protofy in the future. 

Current Libraries that need to be updated:
* Material Dialogs


## How to Contribute

When attempting to contribute to this project please use the normal "Fork and Pull" Git workflow. 

* Fork Protofy
* Clone your copy of Protofy
* Commit changes 
* Push those changes back to your copy of Protofy
* Finally, submit a push request to the main Protofy repo.

## Project Structure

Protofy is split into numerous portions, each with their own purpose. Below I've detailed the main java sections:

* **CustomDataStructures** - Contains data structures that track UI components.
* **DialogPrefs** - Custom dialogs for specific preferences that need more than one setting.
* **NonWhiteboard** - Files that handle app UI and information outside of the WYSIWYG editor.
* **Preferences** - Android preferences for all custom view types and the app.
* **Views** - Custom view types that the editor supports.
* **Whiteboard** - Files for displaying and handling user interactions with the WYSIWYG editor.
* 

### Supported Android Views

Protofy does not support all views availiable to Android developers. This is mainly due to time contraints but here are the views the app currently allows for:

* TextViews
* Buttons
* EditTexts
* ImageViews
* RelativeLayouts

## Built With 

* [Android Studio](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Gradle](https://gradle.org/) - Main Dependency Management
* [Maven](https://maven.apache.org/) - Dependency Management for all future Android support libraries
* Java

## Authors

* **Dakota M. Gallimore** - *Sole Developer* - [Website](http://dakotagallimore.com)

## License

This project is licensed under the GNU GPLv3 License - see the LICENSE.txt file for details

## Acknowledgments

All of the Third-Party developer libraries that allowed me to focus on the app logic. These include but are not limited too:
* Dexafree - MaterialList
* BorderMenu
* TourGuide
* ColorPicker
* Navasmdc - Material Design Library
* Afollestad - Material Dialogs