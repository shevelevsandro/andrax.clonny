package com.thecrackertechnology.dragonterminal.ui.pm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import com.thecrackertechnology.andrax.R
import com.thecrackertechnology.dragonterminal.ui.pm.adapter.holder.PackageViewHolder
import com.thecrackertechnology.dragonterminal.ui.pm.model.PackageModel
import java.util.*

class PackageAdapter(context: Context, comparator: Comparator<PackageModel>, private val listener: PackageAdapter.Listener) : SortedListAdapter<PackageModel>(context, PackageModel::class.java, comparator), FastScrollRecyclerView.SectionedAdapter {

    override fun getSectionName(position: Int): String {
        return getItem(position).packageInfo.packageName?.substring(0, 1) ?: "#"
    }

    interface Listener {
        fun onModelClicked(model: PackageModel)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): SortedListAdapter.ViewHolder<out PackageModel> {
        val rootView = inflater.inflate(R.layout.item_package, parent, false)
        return PackageViewHolder(rootView, listener)
    }
}
