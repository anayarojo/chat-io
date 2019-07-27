package com.anayarojo.chatio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MessageAdapter(var context: Context, items: ArrayList<Message>) : BaseAdapter() {

    var items: ArrayList<Message>? = null

    init {
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var view: View? = convertView

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.message_template, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as? ViewHolder
        }

        val item = getItem(position)as Message

        holder?.tvUser?.text = item.user
        holder?.tvText?.text = item.text
        holder?.tvDate?.text = item.date

        val resourceImage = context.getResources().getIdentifier("ic_user_profile_${item.avatar}", "drawable", context.getPackageName())
        holder?.ivAvatar?.setImageResource(resourceImage)

        return  view!!
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    private class ViewHolder(view: View) {
        var ivAvatar: ImageView? = null
        var tvUser: TextView? = null
        var tvText: TextView? = null
        var tvDate: TextView? = null

        init {
            this.ivAvatar = view.findViewById(R.id.template_ivAvatar)
            this.tvUser = view.findViewById(R.id.template_tvUser)
            this.tvText = view.findViewById(R.id.template_tvText)
            this.tvDate = view.findViewById(R.id.template_tvDate)
        }
    }
}