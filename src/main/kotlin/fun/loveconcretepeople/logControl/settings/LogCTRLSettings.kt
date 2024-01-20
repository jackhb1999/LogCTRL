package `fun`.loveconcretepeople.logControl.settings

import com.intellij.lang.javascript.psi.JSIfStatement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable
import org.lso.logit.settings.DEFAULT_LOGIT_PATTERN


internal const val DEFAULT_LOGIT_PATTERN = """console.log('CTRL','{FP}:{LN}'," => ", $$);"""

@State(name = "LogCTRLSettings", storages = [(Storage("log_it.xml"))])
class LogCTRLSettings  : PersistentStateComponent<LogCTRLSettings> {

    var pattern: String = DEFAULT_LOGIT_PATTERN
    var version = "Unknown"

    companion object {
        val instance: LogCTRLSettings
            get() = ApplicationManager.getApplication().getService(LogCTRLSettings::class.java)
    }

    @Nullable
    override fun getState(): LogCTRLSettings = this

    override fun loadState(state: LogCTRLSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
}