# Jungle Jumper

This game is heavily inspired (a complete knock off) of the popular game Jump King. 

It was created as a school project and thus is fully developed in Java without any external libraries. One of the goal was to incoorperate as many design/software patterns as posible and so the whole structure is based on the MVC (Model View Control) pattern. The view aspect is handled through javas swing library. For the controller, we created our own simple Game-Engine which mainly uses Javas built in geometry classes to calculate the collissions between the different game objects.

On top of that we built a simple [Rest-API](https://github.com/zelythia/JumpKing-ish/tree/master/RestApi) with node.js to offer a global leaderboard for out classmates. This implementation ins't in any way secure as it can't verify the origion of the request and only relies on a session-id for verification and so it is not recommended to use it in a normal production environment.

Feel free to use this project in any way you want, take inspiration or build off it. 

<hr>

![Screenshot](https://github.com/zelythia/JumpKing-ish/blob/master/screenshot.png)
