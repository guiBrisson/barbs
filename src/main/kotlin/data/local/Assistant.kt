package data.local

import model.assistant.AssistantData

// Global singleton assistant
object Assistant {
    var current: AssistantData? = null
}
