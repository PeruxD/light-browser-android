package com.example.lightbrowser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightbrowser.databinding.ItemExtensionBinding

class ExtensionAdapter(
    private val extensions: List<Extension>,
    private val onToggle: (Extension) -> Unit,
    private val onDelete: (Extension) -> Unit
) : RecyclerView.Adapter<ExtensionAdapter.ExtensionViewHolder>() {

    inner class ExtensionViewHolder(val binding: ItemExtensionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(extension: Extension) {
            binding.extensionName.text = extension.name
            binding.extensionToggle.isChecked = extension.enabled
            binding.extensionToggle.setOnCheckedChangeListener { _, isChecked ->
                onToggle(extension.copy(enabled = isChecked))
            }
            binding.deleteButton.setOnClickListener {
                onDelete(extension)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtensionViewHolder {
        val binding = ItemExtensionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExtensionViewHolder(binding)
    }

    override fun getItemCount(): Int = extensions.size

    override fun onBindViewHolder(
        holder: ExtensionViewHolder,
        position: Int
    ) {
        holder.bind(extensions[position])
    }
}