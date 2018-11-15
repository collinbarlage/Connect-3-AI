# connect-3-AI

*Collin Barlage*

Implemented in Java

## How to run in Unix:

`sh run.sh [command] [argument]`

## AlphaBeta Pruning vs Vanilla MiniMax

Running `minimax` from the blank state (empty board) yields search times > 3 ms on the first search.
From then on search time are around ~ 1 ms per turn.

Running `alphabeta` from the blank state yields search times < 2ms on the first search!
From then on search time are around ~ .7 ms per turn.

As you can see the `prune()` function in the Board class makes searching through the tree much quicker via to the alpha beta pruning algorithm.

