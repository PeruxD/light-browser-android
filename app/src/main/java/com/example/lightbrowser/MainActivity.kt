package com.example.lightbrowser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lightbrowser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: BrowserPreferences
    private var extensions = mutableListOf<Extension>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = BrowserPreferences(this)
        loadExtensions()

        binding.extensionList.layoutManager = LinearLayoutManager(this)
        updateExtensionList()

        binding.openButton.setOnClickListener {
            val url = binding.urlInput.text.toString().trim()
            if (url.isNotEmpty()) {
                val intent = Intent(this, BrowserActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingresa una URL", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addExtensionButton.setOnClickListener {
            val ext = Extension(
                id = "custom_${System.currentTimeMillis()}",
                name = "Extensión #${extensions.size + 1}",
                script = """
                    (() => {
                        console.log("Extensión cargada");
                        document.body.style.border = "2px solid #1E88E5";
                    })();
                """.trimIndent()
            )

            extensions.add(ext)
            prefs.saveExtensions(extensions)
            updateExtensionList()
            Toast.makeText(this, "Extensión añadida", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadExtensions() {
        extensions = prefs.loadExtensions().toMutableList()
        if (extensions.isEmpty()) {
            val demo = Extension(
                id = "demo_adblock",
                name = "Bloqueador de anuncios",
                script = """
                    (() => {
                        const removeAds = () => {
                            document.querySelectorAll('iframe, ins, .ads, [id*="ad"], [class*="ad"]').forEach(el => el.style.display = 'none');
                        };
                        document.addEventListener('DOMContentLoaded', removeAds);
                        setInterval(removeAds, 1000);
                    })();
                """.trimIndent()
            )
            extensions.add(demo)
            prefs.saveExtensions(extensions)
        }
    }

    private fun updateExtensionList() {
        binding.extensionList.adapter = ExtensionAdapter(
            extensions,
            onToggle = { ext ->
                val index = extensions.indexOfFirst { it.id == ext.id }
                if (index >= 0) {
                    extensions[index] = ext
                    prefs.saveExtensions(extensions)
                    updateExtensionList()
                }
            },
            onDelete = { ext ->
                extensions.removeAll { it.id == ext.id }
                prefs.saveExtensions(extensions)
                updateExtensionList()
                Toast.makeText(this, "Extensión eliminada", Toast.LENGTH_SHORT).show()
            }
        )
    }
}