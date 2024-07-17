# SignalR Burp Extension

_By [Nick Coblentz](https://www.linkedin.com/in/ncoblentz/)_

__The SignalR Burp Extension is made possible by [Virtue Security](https://www.virtuesecurity.com), the Application Penetration Testing consulting company I work for.__

## About

The __SignalR Burp Extension__ annotates HTTP long polling requests with the hub name and method name being called. Additionally, it provides an HTTP request message editor with prettified JSON to view and edit the SignalR message contents.

## How to Use It

- Build it with `gradlew shadowJar`
- Add the extension in burp from the `build/libs/MontoyaSignalR-x.y-all.jar` folder where `x.y` represents the build version