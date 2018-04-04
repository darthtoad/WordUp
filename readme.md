# _Word Up_

#### _A program that gives information about words_

#### By _**Sam Gespass**_

## Description

_This app allows a user to search for a word and get information about that word._

## Specs

| Spec | Input | Output |
| :-------------     | :------------- | :------------- |
| It can return a list of definitions for a given word | "bear" | List of definitions for bear (using Wordnik API) |
| It can return a list of definitions from a specific dictionary | "Wiktionary" | List of definitions from Wiktionary |
| It can return a list of words that a user has "favorited" | view favorites | List of Favorites |
| It can save new words | save word | Saved! |
| It can remove saved words | remove word | reloads favorites |
| It can change the order of saved words | drag word | changes order |
| It can Google a word in the user's browser after looking up the definition | press "Google word" | Opens Google in browser |
| It can Wikipedia a word in the user's browser | press "See Wikipedia" | Opens Wikipedia in browser |

## Future Features

* Have an activity for related words

## Setup/Installation Requirements

* Click on the following [link](https://github.com/darthtoad/word-up) to download Word Up
* Open Android Studio (preferably version 3.0.1)
* Open the project in Android Studio
* Go to [Giphy](https://developers.giphy.com/) and get an API key
* Create a file in your root directory called "gradle.properties". This file should have the following content: org.gradle.jvmargs=-Xmx1536m  GiphyToken = "API KEY HERE"
* Run project

## Known Bugs

_This project currently has no known bugs. If you find any, please [message](mailto:darth.toad@gmail.com) me._

## Technologies Used

* _Java_
* _Android Studio_
* _Git_
* _GitHub_
* _XML_
* _Wordnik API_
* _GIPHY API_

### License

Copyright (c) 2017 ****_Sam Gespass_****

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
