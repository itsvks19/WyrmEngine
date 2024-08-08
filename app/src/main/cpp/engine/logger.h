//
// Created by Vivek on 8/8/2024.
//

#ifndef WYRMENGINE_LOGGER_H
#define WYRMENGINE_LOGGER_H

#include <android/log.h>

#define TAG "WyrmNative"

#define LogI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LogE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LogD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LogW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)

#endif//WYRMENGINE_LOGGER_H
