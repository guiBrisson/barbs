# Improvements

### Technical
1. **Separate current packages in main.kotlin in its own module**:
   Modularization enables opportunities for code sharing and building multiple apps from the same foundation.
2. **Add a dependency manager (libs.version)**:
   allow you to add and maintain dependencies and plugins in a scalable way. Using these catalogs makes it easier to 
   manage dependencies and plugins when you have multiple modules.
3. **Add a string resource files**: Having a string resource file makes it easy to translate the app
4. **Create DTO classes**:
   It prevents tight coupling between entities, such as the domain model and presentation layer of an application.


### UI
1. **Handle UI state errors**:
    The app handles if there is an error, so it does not crash, but in case there is one nothing is shown to the user.
2. **Empty chat state**:
    When no thread is selected the chat screen is blank. Should tell the user to select or create one.
3. **Handle problem with animations**:
    There are some small wierd animation problems when the sidebar is in transition between collapse/expand.
4. **Markdown support**:
    The AI completions usually comes in form of a markdown.
5. **Scrollbar for the Chat**


### Extra features
1. **Audio to text conversion**: A feature for handling text to audio conversion and vice versa.
2. **User's preferences**: Save the users preferences, such as preferred theme.
3. **Accessibility support**: Jetpack compose have a few accessibility features that should be implemented.

