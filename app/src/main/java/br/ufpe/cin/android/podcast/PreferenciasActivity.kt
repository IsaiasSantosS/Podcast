package br.ufpe.cin.android.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

//Chamar a tela de preferencia para alterar o link do feed de podcast
class PreferenciasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        //Após criar o fragmento, use o código abaixo para exibir
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.preferencias,PrefsFragment())
                .commit()
    }

    class PrefsFragment: PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.preferencias)
        }
    }
}