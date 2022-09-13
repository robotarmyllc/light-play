#!/bin/bash
g++ -I"/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/include" -I"/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers" -I"/usr/local/include" -c LP_DMX.cpp
 
g++ -v -dynamiclib -lftd2xx -o liblpdmx.jnilib LP_DMX.o