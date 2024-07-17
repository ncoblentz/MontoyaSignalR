package com.nickcoblentz.montoya

import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import burp.api.montoya.http.message.params.HttpParameterType
import burp.api.montoya.proxy.http.InterceptedRequest
import burp.api.montoya.proxy.http.ProxyRequestHandler
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction
import burp.api.montoya.ui.editor.extension.EditorCreationContext
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpRequestEditor
import burp.api.montoya.ui.editor.extension.HttpRequestEditorProvider
import org.json.JSONObject

class MontoyaSignalRExtension : BurpExtension, HttpRequestEditorProvider, ProxyRequestHandler {
    private lateinit var Logger: MontoyaLogger
    private val PluginName: String = "SignalR Extension"
    private lateinit var Api: MontoyaApi


    override fun initialize(api: MontoyaApi?) {
        if (api == null) {
            return
        }
        Api = api

        Logger = MontoyaLogger(api, MontoyaLogger.DebugLogLevel)
        api.extension().setName(PluginName)
        Logger.debugLog( "Plugin Starting...")
        api.userInterface().registerHttpRequestEditorProvider(this);
        api.proxy().registerRequestHandler(this)
        Logger.debugLog("Finished")
        Logger.debugLog("Check out: https://blog.3d-logic.com/2015/03/29/signalr-on-the-wire-an-informal-description-of-the-signalr-protocol/")
    }

    override fun provideHttpRequestEditor(creationContext: EditorCreationContext?): ExtensionProvidedHttpRequestEditor {
        return SignalRProvidedHttpRequestEditor(Api, creationContext)
    }

    override fun handleRequestReceived(interceptedRequest: InterceptedRequest?): ProxyRequestReceivedAction {
        return ProxyRequestReceivedAction.continueWith(interceptedRequest)
    }

    override fun handleRequestToBeSent(interceptedRequest: InterceptedRequest?): ProxyRequestToBeSentAction {
        interceptedRequest?.let {
            Logger.debugLog("Intercept request")
            if(it.hasParameter("data",HttpParameterType.URL)) {
                Logger.debugLog("has data")
                var jsonData = JSONObject(
                    Api.utilities().urlUtils().decode(it.parameterValue("data", HttpParameterType.URL))
                )
                Logger.debugLog("append notes")
                it.annotations().appendNotes(jsonData.getString("H")+"."+jsonData.getString("M"))
                Logger.debugLog("done appending notes")
            }
        }
        Logger.debugLog("returning")
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest)
    }


}