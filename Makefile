PHONY: all clean

all: run

Main.class: Main.java IO.java Board.java Path.java Player.java RandomPlayer.java MinimaxPlayer.java MinimaxAlphaBetaPlayer.java
	javac $^

run: Main.class
	java Main $(ARGS)

clean:
	-rm -f *.class
