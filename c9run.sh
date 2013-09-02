#!/bin/bash

cd ~/600552/proto-oct/src
java -cp ~/lib/pkg/clojure-1.4.0/clojure-1.4.0.jar:. clojure.main -e "(load-file \"proto_oct/genfile.clj\")(in-ns 'proto-oct.genfile)" -r

