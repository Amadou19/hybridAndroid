package com.example.hybrid_android

import FlutterMessageDataApi
import HostMessageDataApi
import MessageData
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.hybrid_android.FlutterHybridApplication.Companion.ENGINE_ID
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine


class FlutterHybridActivity : FlutterActivity() {

    companion object {
        private const val EXTRA_MESSAGE_DATA = "EXTRA_MESSAGE_DATA";

        fun newIntent(context: Context, messageData: MessageData): Intent {
            return CachedEngineBookIntentBuilder(ENGINE_ID)
                .build(context)
                .putExtra(
                    EXTRA_MESSAGE_DATA,
                    messageData.toList().toTypedArray()
                )

        }

        fun getFromResultIntent(resultIntent: Intent): MessageData {
            return MessageData.fromList((resultIntent.getSerializableExtra(EXTRA_MESSAGE_DATA) as Array<Any>).toList())
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        HostMessageDataApi.setUp(flutterEngine.dartExecutor, HostMessageDataApiHandler())

        val message = MessageData.fromList((intent.getSerializableExtra(EXTRA_MESSAGE_DATA) as Array<Any>).toList())
        FlutterMessageDataApi(flutterEngine.dartExecutor).displayMessage(
            MessageData(
                title = message.title,
                subtitle = message.subtitle
            )
        ) {
            // not care to callback
        }
    }

    class CachedEngineBookIntentBuilder(engineId: String) :
        CachedEngineIntentBuilder(FlutterHybridActivity::class.java, engineId) {}

    inner class HostMessageDataApiHandler : HostMessageDataApi {
        override fun finishDisplayMessage(messageData: MessageData) {
            print(messageData)
            setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_MESSAGE_DATA, messageData.toList().toTypedArray()))
            finish()
        }

        override fun cancel() {
            print("cancel")
        }

    }
}