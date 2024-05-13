package com.stori.interviewtest.data.error

import com.stori.interviewtest.data.error.UserError.Type.NETWORK
import com.stori.interviewtest.data.error.UserError.Type.UNHANDLED
import java.util.Objects

class UserError {
    enum class Type {
        EXCEPTION,
        HTTP,
        NETWORK,
        UNHANDLED
    }

    var type: Type
        private set
    var message: String = ""
        private set
    private var resourceId: Int? = null

    constructor(type: Type) {
        this.type = type
        message = ""
    }

    constructor(type: Type, message: String?) {
        this.type = type
        this.message = message ?: ""
    }

    constructor(type: Type, resourceId: Int?) {
        this.type = type
        this.resourceId = resourceId
    }

    /**
     * This method returns a message ready for being presented to the user.
     *
     * @param messageParser contains the implementation to get messages from context
     * @return A message for presenting to the user. `null` if message doesn't exists.
     */
    fun getMessage(messageParser: MessageParser): String? {
        if (message != null) {
            return message
        }
        if (resourceId != null) {
            return messageParser.getString(resourceId!!)
        }
        when (type) {
            NETWORK -> return messageParser.networkErrorMessage
            UNHANDLED -> return messageParser.unhandledErrorMessage
            else -> {}
        }
        return null
    }

    interface MessageParser {
        fun getString(resourceId: Int): String?
        val networkErrorMessage: String?
        val unhandledErrorMessage: String?
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val userError = other as UserError
        return type == userError.type && message == userError.message && resourceId == userError.resourceId
    }

    override fun hashCode(): Int {
        return Objects.hash(type, message, resourceId)
    }
}
