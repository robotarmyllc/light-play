/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_robotarmy_dmx_DMX */

#ifndef _Included_com_robotarmy_dmx_DMX
#define _Included_com_robotarmy_dmx_DMX
#ifdef __cplusplus
extern "C" {
#endif
#undef com_robotarmy_dmx_DMX_DMX_TX
#define com_robotarmy_dmx_DMX_DMX_TX 0L
#undef com_robotarmy_dmx_DMX_DMX_RX
#define com_robotarmy_dmx_DMX_DMX_RX 1L
/*
 * Class:     com_robotarmy_dmx_DMX
 * Method:    getList
 * Signature: ()[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_com_robotarmy_dmx_DMX_getList
  (JNIEnv *, jclass);

/*
 * Class:     com_robotarmy_dmx_DMX
 * Method:    connect
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_connect
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     com_robotarmy_dmx_DMX
 * Method:    disconnect
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_robotarmy_dmx_DMX_disconnect
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_robotarmy_dmx_DMX
 * Method:    setValue
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_setValue
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_robotarmy_dmx_DMX
 * Method:    getValue
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_getValue
  (JNIEnv *, jclass, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
