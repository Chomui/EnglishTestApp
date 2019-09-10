package chi.englishtest.com.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import chi.englishtest.com.R

class LoadingDialog private constructor(): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if(dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    companion object {

        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }

        fun start(manager: FragmentManager): LoadingDialog {
            val dialog = LoadingDialog.newInstance()
            dialog.isCancelable = false
            val transaction = manager.beginTransaction()
            transaction.add(dialog, "Loading Dialog")
            transaction.commitAllowingStateLoss()
            return dialog
        }
    }


}