# minigames

## What is it?

Programmable minigames via Json rest api.

This project goal is creating a small self hosting web service which provide multiple mini game implementation and a developer can competing with other dev's bot.

Here is a schematic workflow
```
  +------------+      +-----------+    +-----------+
  |            +----->+           +---->           |
  |   my bot   |      |game server|    |foreign bot|
  |            <------+           <----+           |
  +------------+      +-----------+    +-----------+

   *stategy             *game rules     *strategy
   *foreign movements   *players        *foreign movements
                        *results
```

## Usage
Starting the server
```
git clone https://github.com/Pozo/minigames && cd minigames
gradle appRun
```
The tictactoe game currently working separately from the server. Take a look at **com.github.pozo.tictactoe.App**

## Todo
 - wire together the initial tictactoe implementation
 - play a sample game via rest api
 - games
   - tictactoe
   - battleships
   - upwords
   - ...

## Licensing

Please see the file called LICENSE.

## Contact

  Zoltan Polgar - pozo@gmx.com
  
  Please do not hesitate to contact me if you have any further questions. 
