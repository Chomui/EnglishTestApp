package chi.englishtest.com.data.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.text.TextUtils
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat

abstract class BaseAlertDialog : android.app.AlertDialog {

    protected constructor(context: Context) : super(context) {}

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener) {
    }

    protected constructor(context: Context, @StyleRes themeResId: Int) : super(
        context,
        themeResId
    ) {
    }

    class Builder : AlertDialog.Builder {

        private var buttonColor = -1

        constructor(context: Context) : super(context) {}

        constructor(context: Context, @ColorRes buttonColor: Int) : super(context) {
            this.buttonColor = buttonColor
        }

        override fun setTitle(title: CharSequence?): AlertDialog.Builder {
            val string = SpannableString(title)
            return super.setTitle(string)
        }

        override fun setTitle(@StringRes titleId: Int): AlertDialog.Builder {
            val title = context.getString(titleId)
            return super.setTitle(titleId)
        }

        override fun setMessage(message: CharSequence?): AlertDialog.Builder {
            if (TextUtils.isEmpty(message)) {
                return super.setMessage("")
            }
            val string = SpannableString(message)
            return super.setMessage(string)
        }

        override fun setMessage(@StringRes messageId: Int): AlertDialog.Builder {
            val message = context.getString(messageId)
            return setMessage(message)
        }

        override fun show(): AlertDialog {
            val alertDialog = create()
            alertDialog.setOnShowListener {
                val color = ResourcesCompat.getColor(
                    context.resources,
                    if (buttonColor == -1) android.R.color.holo_purple else buttonColor,
                    context.theme
                )
                var button: Button?
                val buttons = intArrayOf(
                    DialogInterface.BUTTON_NEGATIVE,
                    DialogInterface.BUTTON_NEUTRAL,
                    DialogInterface.BUTTON_POSITIVE
                )
                for (buttonType in buttons) {
                    button = alertDialog.getButton(buttonType)
                    button?.setTextColor(color)
                }
            }
            alertDialog.show()
            return alertDialog
        }
    }
}