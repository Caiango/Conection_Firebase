package com.example.conection_firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(val mCtx: Context,val layoutResId: Int, val userList: List<Usuário>) :
    ArrayAdapter<Usuário>(mCtx, layoutResId, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewNome = view.findViewById<TextView>(R.id.textViewNome)
        val textViewFone = view.findViewById<TextView>(R.id.textViewFone)

        val user = userList[position]

        textViewNome.text = user.nome
        textViewFone.text = user.telefone

        return view
    }


}