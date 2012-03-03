#!/bin/sh
echo $@
#
# Thanks to Peter Torngaard
#
full=$(dirname $0)
echo ${full}/lib/vmap.jar
java -jar ${full}/lib/vmap.jar $@

