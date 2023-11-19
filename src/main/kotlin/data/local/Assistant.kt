package data.local

import model.assistant.AssistantData

// Global singleton assistant
object Assistant {
    lateinit var current: AssistantData
}
