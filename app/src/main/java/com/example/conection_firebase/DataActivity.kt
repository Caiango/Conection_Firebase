package com.example.conection_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.mbms.StreamingServiceInfo
import android.util.Log
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data.*

class DataActivity : AppCompatActivity() {

    var edttelefone: EditText? = null
    var edtnome: EditText? = null
    lateinit var btnSalvar: Button
    lateinit var btnSalvar2: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    lateinit var ref: DatabaseReference
    lateinit var ref2: DatabaseReference
    lateinit var IDUSU: String
    lateinit var userList: MutableList<Usuário>
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        userList = mutableListOf()
        //Referenciando Documento Usuários
        ref = FirebaseDatabase.getInstance().getReference("Usuários")
        //Referenciando Documento Tarefa
        ref2 = FirebaseDatabase.getInstance().getReference("Tarefa")

        edttelefone = findViewById(R.id.editFone)
        edtnome = findViewById(R.id.editNome)
        btnSalvar = findViewById(R.id.buttonSave)
        btnSalvar2 = findViewById(R.id.buttonMostrar)
        btnUpdate = findViewById(R.id.buttonUP)
        btnDelete = findViewById(R.id.buttonDel)
        listView = findViewById(R.id.listView)


        btnSalvar.setOnClickListener { insert() }

        btnSalvar2.setOnClickListener { get() }

        btnUpdate.setOnClickListener {
            ref.child("INSERT 2").child("nome").setValue("CAIO LINDO").addOnCompleteListener {
                Toast.makeText(this, "UPDATE FEITO COM SUCESSO", Toast.LENGTH_LONG).show()
            }
            ref.child("INSERT 2").child("telefone").setValue("999999999").addOnCompleteListener {
                Toast.makeText(this, "UPDATE FEITO COM SUCESSO", Toast.LENGTH_LONG).show()
            }
        }
        btnDelete.setOnClickListener { remover() }

    }

    private fun writeNewUser(UID: String, name: String, telefone: String) {
        val user = Usuário(UID, name, telefone)

        ref.child(UID).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "SEGUNDA OPÇÃO DE INSERT CONFIRMADA", Toast.LENGTH_LONG).show()
            IDUSU = UID
        }
    }

    private fun remover() {
        ref.child("INSERT 2").removeValue().addOnCompleteListener {
            Toast.makeText(this, "REMOÇÃO FEITA COM SUCESSO", Toast.LENGTH_LONG).show()
        }
    }

    private fun get() {
        ref.orderByChild("nome").equalTo("MERDA").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0!!.exists()) {
                    userList.clear()
                    for (h in p0.children) {
                        val user = h.getValue(Usuário::class.java)
                        userList.add(user!!)
                    }

                    val adapter = UserAdapter(applicationContext, R.layout.showusu, userList)
                    listView.adapter = adapter

                }

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun insert() {
        var nome: String = editNome.text.toString().trim()
        var telefone: String = editFone.text.toString().trim()

        //Na aplicação final eu vou trocar o UID pelo ID gerado logando na conta Google
        val UID = ref.push().key.toString()
        val UID2 = ref2.push().key.toString()
        val USU = Usuário(UID, nome, telefone)

        //INSERT FIREBASE NO DOCUMENTO USUARIO
        ref.child(UID).setValue(USU).addOnCompleteListener {
            Toast.makeText(this, "INSERT BEM SUCEDIDO", Toast.LENGTH_LONG).show()
        }
        //INSERT FIREBASE NO DOCUMENTO TAREFA
        ref2.child(UID2).setValue(USU).addOnCompleteListener {
            Toast.makeText(this, "INSERT BEM SUCEDIDO", Toast.LENGTH_LONG).show()
        }
    }

}

