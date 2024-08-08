//
// Created by Vivek on 8/8/2024.
//

#ifndef WYRMENGINE_SERIALIZATION_H
#define WYRMENGINE_SERIALIZATION_H

#include <fstream>
#include <iostream>
#include <string>
#include <type_traits>

#include "logger.h"

template<typename T>
void serialize(const T& obj, const std::string& filename) {
  static_assert(std::is_trivially_copyable<T>::value, "T must be trivially copyable");

  std::ofstream outFile(filename, std::ios::binary);
  if (!outFile) {
    LogE("SERIALIZATION: ", "Failed to open file for writing.");
    return;
  }
  outFile.write(reinterpret_cast<const char*>(&obj), sizeof(T));
  outFile.close();
}

template<typename T>
void deserialize(T& obj, const std::string& filename) {
  static_assert(std::is_trivially_copyable<T>::value, "T must be trivially copyable");

  std::ifstream file(filename, std::ios::binary);
  if (!file) {
    LogE("SERIALIZATION: ", "Failed to open file for reading.");
    return;
  }
  file.read(reinterpret_cast<char*>(&obj), sizeof(T));
  file.close();
}

#endif//WYRMENGINE_SERIALIZATION_H
