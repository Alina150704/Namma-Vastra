package com.example.nammavastra.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammavastra.R
import com.example.nammavastra.model.Message
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendBtn: ImageButton

    private val messages = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter

    private val handler = Handler(Looper.getMainLooper())

    private var sellerName = ""
    private var productName = ""
    private var productPrice = 4500.0
    private var shipping = 100.0
    private var taxRate = 0.05

    private var state = "START"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sellerName = intent.getStringExtra("SELLER_NAME") ?: "Weaver"
        productName = intent.getStringExtra("PRODUCT_NAME") ?: "Saree"
        productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 4500.0)

        recyclerView = findViewById(R.id.messagesRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendBtn = findViewById(R.id.btnSend)

        adapter = MessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 🔥 Start natural conversation automatically
        startConversation()

        sendBtn.setOnClickListener {
            val text = messageInput.text.toString().trim()
            if (text.isNotEmpty()) {
                sendUserMessage(text)
                messageInput.text.clear()
                handleMessage(text)
            }
        }
    }

    private fun startConversation() {
        addSellerMessage("Hi 😊 This $productName is available!")
        addSellerMessage("It’s pure handwoven silk ✨")
        addSellerMessage("We have colors like red, green, maroon, blue.\nWhich one do you like?")
        state = "COLOR"
    }

    private fun handleMessage(msg: String) {

        when (state) {

            "COLOR" -> {
                addSellerMessage("Nice choice 😄 That color looks beautiful!")
                addSellerMessage("It’s ₹$productPrice.\nDo you want to order?")
                state = "CONFIRM"
            }

            "CONFIRM" -> {

                if (msg.contains("yes", true) ||
                    msg.contains("buy", true) ||
                    msg.contains("order", true)
                ) {

                    val total = calculateTotal()

                    addSellerMessage("Perfect 👍\nTotal is ₹$total including shipping & GST.")
                    addSellerMessage("You can pay and tell me once done 😊")

                    state = "PAYMENT"

                } else {
                    addSellerMessage("No problem 😊 Let me know if you need anything else!")
                }
            }

            "PAYMENT" -> {

                if (msg.contains("done", true) || msg.contains("paid", true)) {

                    addSellerMessage("Got it 👍 Checking payment...")

                    handler.postDelayed({

                        addSellerMessage("✅ Payment confirmed! Thank you 😊")

                        handler.postDelayed({

                            val intent = Intent(this@ChatActivity, PaymentSummaryActivity::class.java)
                            intent.putExtra("PRODUCT_NAME", productName)
                            intent.putExtra("SELLER_NAME", sellerName)
                            intent.putExtra("PRODUCT_PRICE", productPrice)
                            intent.putExtra("SHIPPING_COST", shipping)
                            intent.putExtra("TAX_RATE", taxRate)

                            startActivity(intent)

                        }, 1200)

                    }, 1200)

                } else {
                    addSellerMessage("Just type 'done' once payment is completed 😊")
                }
            }
        }
    }

    private fun calculateTotal(): Double {
        return productPrice + shipping + (productPrice * taxRate)
    }

    private fun sendUserMessage(text: String) {
        messages.add(Message(UUID.randomUUID().toString(), text, true, System.currentTimeMillis()))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun addSellerMessage(text: String) {
        messages.add(Message(UUID.randomUUID().toString(), text, false, System.currentTimeMillis()))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    inner class MessageAdapter(private val list: List<Message>) :
        RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

        inner class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
            val userMsg: TextView = view.findViewById(R.id.userMessageText)
            val sellerMsg: TextView = view.findViewById(R.id.sellerMessageText)
            val userLayout: android.view.View = view.findViewById(R.id.userMessageLayout)
            val sellerLayout: android.view.View = view.findViewById(R.id.sellerMessageLayout)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_message, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val msg = list[position]

            if (msg.isSentByUser) {
                holder.userLayout.visibility = android.view.View.VISIBLE
                holder.sellerLayout.visibility = android.view.View.GONE
                holder.userMsg.text = msg.text
            } else {
                holder.userLayout.visibility = android.view.View.GONE
                holder.sellerLayout.visibility = android.view.View.VISIBLE
                holder.sellerMsg.text = msg.text
            }
        }

        override fun getItemCount() = list.size
    }
}