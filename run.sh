#!/bin/sh
if [ "$#" -gt 1 ]; then
	make ARGS="$1 '$2'"
else
	make ARGS=$1
fi
