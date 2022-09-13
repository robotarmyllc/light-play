#ifndef CC_SDK_H
#define CC_SDK_H
#ifdef MAINFILE
    #define EXTERN
#else
    #define EXTERN extern
#endif

#include "DeltaBrain.h"
#include "DeltaServos.h"
#include "DeltaKinetics.h"
#include "ModeChicken.h"
#include "ModeSerialDirect.h"
#include "ModeSerialXYZ.h"

EXTERN  DeltaBrain    brain;
EXTERN  DeltaKinetics delta;
EXTERN  DeltaServos   servos;

EXTERN  ModeChicken         modeChicken;
EXTERN  ModeSerialDirect    modeSerialDirect;
EXTERN  ModeSerialXYZ       modeSerialXYZ;

#endif
