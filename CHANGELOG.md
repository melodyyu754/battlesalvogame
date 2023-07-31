## Changes to PA03 Design

1. Created a new AIPlayer constructor and set AI ships field in setup
- accounts for delayed setup in Server game by initializing fields to null/empty and
  altering them during the setup

2. Altered calculation of remaining shots
- if the Player has more ships than there are unhit coordinates on their opponent's board,
  they may only shoot a Salvo the size of the unhit coordinates

3. Made Ai more intelligent
- Our Ai now shoots first from a worklist, which is a list of ships that surround a hit coordinate.

3. Edited FakeRandom()
- Changed nextBoolean to use consistently switch between horizontal and vertical,
  and used possible integers 0-8 (an odd number), to ensure alternating patterns.

4. Added methods in original Ship class
- start() and direction() methods ease conversion from Ship to ShipAdapter

5. Fixed bug in Coord class
- board was not being correctly initialized because x and y were being mixed up