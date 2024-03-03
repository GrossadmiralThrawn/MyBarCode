package com.example.mybarcode




import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity




class CreateCodeActivity : AppCompatActivity() {
    private lateinit var returnButton:        Button
    private lateinit var createQRCodeButton:  Button
    private lateinit var createBarCodeButton: Button
    private lateinit var inputEditText:       EditText
    private lateinit var badInputTextView:    TextView
    private lateinit var codeText:            String
    private lateinit var showCodeIntent:      Intent




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_code)
        returnButton         = findViewById(R.id.returnFromCreateButton)
        createQRCodeButton   = findViewById(R.id.createQRCodeButton)
        createBarCodeButton  = findViewById(R.id.createBarCodeButton)
        inputEditText        = findViewById(R.id.toCodeEditText)
        badInputTextView     = findViewById(R.id.badInputTextView)
        showCodeIntent       = Intent(this, ShowCodeActivity::class.java)



        createQRCodeButton.setOnClickListener {
            if (getInputText()) {
                showCodeIntent.putExtra("codeText", codeText)
                showCodeIntent.putExtra("codeType", "QR")
                startActivity(showCodeIntent)
                finish()
            }
            else {
                badInputTextView.text = getText(R.string.bad_input_textview)
            }
        }


        createBarCodeButton.setOnClickListener {
            if (getInputText()) {
                showCodeIntent.putExtra("codeText", codeText)
                showCodeIntent.putExtra("codeType", "Bar")
                startActivity(showCodeIntent)
                finish()
            }
            else
            {
                badInputTextView.text = getText(R.string.bad_input_textview)
            }
        }


        returnButton.setOnClickListener {
            finish()
        }
    }




    private fun getInputText(): Boolean {
        codeText = inputEditText.text.toString()
        return codeText != ""
    }
}