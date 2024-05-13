package com.stori.interviewtest.data.error

import com.stori.interviewtest.data.error.UserError.Type


class GenericUserErrorContainer : UserErrorContainer {
    var errorType: Type
        private set
    var message: String? = null
        private set

    constructor(errorType: Type) {
        this.errorType = errorType
    }

    constructor(errorType: Type, message: String?) {
        this.errorType = errorType
        this.message = message
    }

    override val userError: UserError
        get() = UserError(errorType, message)
}
