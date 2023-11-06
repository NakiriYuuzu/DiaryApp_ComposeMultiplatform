package net.yuuzu.diaryapp_composemultiplatform.utils.koin

interface ImageStorage {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(fileName: String): ByteArray?
    suspend fun deleteImage(fileName: String)
}