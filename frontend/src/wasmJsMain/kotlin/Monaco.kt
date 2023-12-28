@file:JsModule("monaco-editor")

import org.w3c.dom.HTMLElement


external object editor : JsAny {
    interface IModelContentChangedEvent : JsAny {

    }
    interface IEditorModel : JsAny {
        fun getValue(): String
        fun setValue(newValue: JsString)
        fun onDidChangeContent(listener: (e: IModelContentChangedEvent) -> Unit)
    }
    open class IEditorOptions : JsAny {
        var automaticLayout: Boolean?
        var readOnly: Boolean?
        var readOnlyMessage: String?
    }
    class IStandaloneEditorConstructionOptions : IEditorOptions {
        var value: String?
        var language: String?
    }
    interface IStandaloneCodeEditor : JsAny {
        fun getModel(): IEditorModel?
        fun updateOptions(newOptions: IEditorOptions)
    }
    fun create(domElement: HTMLElement, options: IStandaloneEditorConstructionOptions): IStandaloneCodeEditor
}
