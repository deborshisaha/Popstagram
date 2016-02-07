# Project 1 - *Popstagram*

**Popstagram** is an android app that allows a user to check out popular photos from Instagram. The app utilizes Instagram API to display images and basic image information to the user.

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

* [*] User can **scroll through current popular photos** from Instagram
* [*] For each photo displayed, user can see the following details:
  * [*] Graphic, Caption, Username
  * [*] Relative timestamp, like count, user profile image

The following **optional** features are implemented:

* [*] User can **pull-to-refresh** popular stream to get the latest popular photos
* [*] Show latest comments for each photo
* [*] Display each photo with the same style and proportions as the real Instagram
* [*] Display each user profile image using a RoundedImageViewDisplay each user profile image using a [RoundedImageView](https://github.com/vinc3m1/RoundedImageView)
* [*] Display a nice default placeholder graphic for each image during loading
* [*] Improved the user interface through styling and coloring

The following **bonus** features are implemented:

* [*] Show last 2 comments for each photo
* [*] Allow user to view all comments for an image within a separate activity or dialog fragment
* [*] Allow video posts to be played in full-screen using the VideoView

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/deborshisaha/Popstagram/blob/master/Popstagram.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

I wanted to display all the comments against a post. I was not able to find any documentation to page comment results.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) - Imageview with round corners
- [ButterKnife](http://jakewharton.github.io/butterknife/) - Boiler plate code to bind views to code

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
