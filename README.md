# PickSomeApp
A Java application made with JavaFX using the RAWG and Gutendex REST APIs. Don't know what to play or read? Let PickSome suggest you something.

![Alt Text](gifExample1.gif)

![Alt Text](gifExample2.gif)

This application currently makes use of the RAWG API, but as this API seems to be in quite a dire state, I'll start making use of another API in the near future.

## How to run?
Make sure picksome.jar and the dependencies folder are in the same directory. If that's the case you can run the program by typing
```
java -p ./dependencies --add-modules ALL-MODULE-PATH -cp picksome.jar com.picksome.picksome.MainApp
```
in the command line and hitting enter.

## Possible future updates
- More sophisticated history component
- The user being able to make and add to collections
- Filters being added to narrow suggestions
- Add movies and series to API selection
- Add more languages
