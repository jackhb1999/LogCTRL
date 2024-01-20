package org.example.demo

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

class HelloAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        // TODO: insert action logic here
        val project: Project? = e.getData<Project>(PlatformDataKeys.PROJECT)

        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val actionManager = EditorActionManager.getInstance()
        val startNewLineHandler = actionManager.getActionHandler(IdeActions.ACTION_EDITOR_START_NEW_LINE)

        val vFile: VirtualFile? = e.getData(PlatformDataKeys.VIRTUAL_FILE)


        val title = "hello world"
        val content = "Welcome to the new world! editor:${editor},vFile:${vFile?.name},line:${editor.caretModel.currentCaret.logicalPosition.line + 2}"
        Messages.showMessageDialog(project, content, title, Messages.getInformationIcon())

    }
}