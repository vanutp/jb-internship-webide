import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLElement
import org.w3c.fetch.*

fun makeMonacoOptions(
    value: String? = null,
    language: String? = null,
    automaticLayout: Boolean? = null,
    readOnly: Boolean? = null,
    readOnlyMessage: String? = null,
): editor.IStandaloneEditorConstructionOptions =
    js(
        """({
            value,
            language,
            automaticLayout,
            readOnly,
            readOnlyMessage,
        })"""
    )

// RequestInit function has null as default for all arguments,
// so if not all arguments are passed, the fetch call fails with
// `Failed to execute 'fetch' on 'Window': Failed to read the 'xxx' property from 'RequestInit':
// The provided value 'null' is not a valid enum value of type xxx.`
fun makeRequestInit(method: String, headers: Headers, body: String): RequestInit =
    js(
        """({
            method,
            headers,
            body,
        })"""
    )

fun main() {
    document.body!!.appendElement("div") {
        classList.add("editor")
        val instance = editor.create(
            this as HTMLElement,
            makeMonacoOptions(
                value = """Loading...""",
                language = "typescript",
                automaticLayout = true,
                readOnly = true,
                readOnlyMessage = "Loading..."
            )
        )
        val model = instance.getModel()!!
        model.onDidChangeContent {
            val headers = Headers()
            headers.append("Content-Type", "text/plain")
            window.fetch(
                "http://127.0.0.1:8000/file.ts",
                makeRequestInit(
                    method = "put",
                    headers = headers,
                    body = model.getValue(),
                )
            )
        }
        window.fetch("http://127.0.0.1:8000/file.ts")
            .then { resp ->
                if (!resp.ok) {
                    throw Error("not ok")
                }
                resp.text().then { text ->
                    model.setValue(text)
                    instance.updateOptions(makeMonacoOptions(readOnly = false))
                    null
                }
                null
            }
    }
}
