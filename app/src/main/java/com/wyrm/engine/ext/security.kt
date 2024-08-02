@file:JvmName("Encryptor")

package com.wyrm.engine.ext

import com.wyrm.engine.Constants
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * @param n must be 128, 192 or 256
 */
@Throws(NoSuchAlgorithmException::class)
fun generateKey(n: Int): SecretKey {
  val keyGenerator = KeyGenerator.getInstance("AES")
  keyGenerator.init(n)
  val key = keyGenerator.generateKey()
  return key
}

fun generateIv(): IvParameterSpec {
  val iv = ByteArray(16)
  SecureRandom().nextBytes(iv)
  return IvParameterSpec(iv)
}

@Throws(
  NoSuchPaddingException::class,
  NoSuchAlgorithmException::class,
  InvalidAlgorithmParameterException::class,
  InvalidKeyException::class,
  BadPaddingException::class,
  IllegalBlockSizeException::class
)
@JvmOverloads
fun encrypt(
  input: String,
  key: SecretKey = Constants.defaultEncryptionKey,
  iv: IvParameterSpec = Constants.defaultEncryptionIv,
  algorithm: String = Constants.DEFAULT_ENCRYPTION_ALGORITHM,
): String {
  val cipher = Cipher.getInstance(algorithm)
  cipher.init(Cipher.ENCRYPT_MODE, key, iv)
  val cipherText = cipher.doFinal(input.toByteArray())
  return Base64.getEncoder().encodeToString(cipherText)
}

@Throws(
  NoSuchPaddingException::class,
  NoSuchAlgorithmException::class,
  InvalidAlgorithmParameterException::class,
  InvalidKeyException::class,
  BadPaddingException::class,
  IllegalBlockSizeException::class
)
@JvmOverloads
fun decrypt(
  cipherText: String,
  key: SecretKey = Constants.defaultEncryptionKey,
  iv: IvParameterSpec = Constants.defaultEncryptionIv,
  algorithm: String = Constants.DEFAULT_ENCRYPTION_ALGORITHM,
): String {
  val cipher = Cipher.getInstance(algorithm)
  cipher.init(Cipher.DECRYPT_MODE, key, iv)
  val plainText = cipher.doFinal(
    Base64.getDecoder().decode(cipherText)
  )
  return String(plainText)
}