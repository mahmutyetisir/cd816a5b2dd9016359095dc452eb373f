package com.myetisir.spacetransporter.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.myetisir.spacetransporter.R
import com.myetisir.spacetransporter.databinding.DialogInfoBinding

class InfoDialog(
    context: Context,
    private val message: String? = null,
    private val cancellable: Boolean = false,
    private val callback: () -> Unit
) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogInfoBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        binding.dialogErrorTxtMessage.text =
            message ?: context.getString(R.string.dialog_error_title_text)
        binding.dialogButton.setOnClickListener {
            dismiss()
            callback()
        }
        setCancelable(cancellable)
    }
}