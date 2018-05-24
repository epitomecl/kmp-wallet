package com.epitomecl.kmpwallet.model.types

import java.lang.Exception

interface KmpType {
    class InsufficientBytesException : Exception() {
        companion object {
            private val serialVersionUID = 1L
        }
    }

    interface Packer {
        fun pack(writer : KmpType.Writer)
    }

    interface Unpacker {
        @Throws(KmpType.InsufficientBytesException::class)
        fun unpack(reader : KmpType.Reader)
    }

    interface Reader {
        @Throws(KmpType.InsufficientBytesException::class)
        fun get(): Byte

        @Throws(KmpType.InsufficientBytesException::class)
        fun getShortLE(): Short

        @Throws(KmpType.InsufficientBytesException::class)
        fun getIntLE(): Int

        @Throws(KmpType.InsufficientBytesException::class)
        fun getLongLE(): Long

        @Throws(KmpType.InsufficientBytesException::class)
        fun getBytes(size : Int): ByteArray

        @Throws(KmpType.InsufficientBytesException::class)
        fun getString(): String

        @Throws(KmpType.InsufficientBytesException::class)
        fun getVariableUint(): Long
    }

    interface Writer {
        fun put(b : Byte)
        fun putShortLE(value : Short)
        fun putIntLE(value : Int)
        fun putLongLE(value : Long)
        fun putBytes(value : ByteArray)
        fun putString(value : String)
        fun toBytes() : ByteArray
        fun length() : Int

        fun putCollection(collection: Collection<Packer>)
        fun putVariableUInt(value: Long)
    }
}