package `fun`.loveconcretepeople.logControl

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.CaretState
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.vfs.VirtualFile
import `fun`.loveconcretepeople.logControl.settings.LogCTRLSettings


class LogCTRLAdd : AnAction("Insert log") {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val actionManager = EditorActionManager.getInstance()
        val startNewLineHandler = actionManager.getActionHandler(IdeActions.ACTION_EDITOR_START_NEW_LINE)

        val vFile: VirtualFile? = e.getData(PlatformDataKeys.VIRTUAL_FILE)

        val logVar = "variableName"?.trim()


        val pattern = LogCTRLSettings.instance.pattern.run {
            replace("{FN}", vFile?.name ?: "filename").replace("{FP}", vFile?.path ?: "file_path")
                .replace("{LN}", (editor.caretModel.currentCaret.logicalPosition.line + 2).toString())
        }

        val insertionPositions = "\\$\\$".toRegex().findAll(pattern)
            .map { it.range.first }
            .toList()

        val lineToInsert = if (logVar == "\n") {
            "\n${pattern.replace("$$", "")}"
        } else
            pattern.replace("$$", "$logVar")


        val line2insert = lineToInsert.replace("<CR>", "")

        val runnable = {

            startNewLineHandler.execute(editor, editor.caretModel.primaryCaret, e.dataContext)

            val offset = editor.caretModel.currentCaret.offset
            editor.document.insertString(offset, line2insert)
        }
        WriteCommandAction.runWriteCommandAction(editor.project, runnable)

        positionCaret(editor, insertionPositions, line2insert, "variableName".replace("<CR>", "").trim())

    }

    private fun positionCaret(
        editor: Editor,
        insertionPositions: List<Int>,
        lineToInsert: String,
        variableName: String
    ) {
        val offset = editor.caretModel.currentCaret.offset
        val logicalPosition = editor.offsetToLogicalPosition(offset)

        editor.caretModel.caretsAndSelections =
            listOf(
                CaretState(
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[0]
                    ),
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[0]
                    ),
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[0] + variableName.length
                    )
                ),
                CaretState(
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[1] + variableName.length - 2
                    ),
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[1] + variableName.length - 2
                    ),
                    LogicalPosition(
                        logicalPosition.line,
                        logicalPosition.column + insertionPositions[1] + variableName.length * 2 - 2
                    )
                )
            )
        //println(editor.caretModel.caretsAndSelections)
    }

}