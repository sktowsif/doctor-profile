package com.project.doctors.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.project.doctors.R
import com.project.doctors.data.entities.Record
import com.project.doctors.data.vm.RecordViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.item_doctor_record.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecordListFragment : Fragment() {

    companion object {
        private val RECORD_COMPARATOR = object : DiffUtil.ItemCallback<Record>() {
            override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean =
                oldItem.firstName == newItem.firstName
                        && oldItem.lastName == newItem.lastName
                        && oldItem.uri == newItem.uri
                        && oldItem.statement == newItem.statement

            override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean =
                oldItem.id == newItem.id

            // Implement this to show animation
            override fun getChangePayload(oldItem: Record, newItem: Record): Any? {
                return super.getChangePayload(oldItem, newItem)
            }
        }

    }

    private val recordViewModel: RecordViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)
        rootView.recordList.adapter = RecordAdapter()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recordViewModel.recordLiveData.observe(viewLifecycleOwner, Observer {
            getAdapter().submitList(it)
        })
    }

    private fun getAdapter() = recordList.adapter as RecordAdapter

    private class RecordAdapter :
        PagedListAdapter<Record, RecordAdapter.ViewHolder>(RECORD_COMPARATOR) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_doctor_record,
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            getItem(position)?.let { holder.bindRecord(it) }
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            fun bindRecord(record: Record) = with(record) {
                itemView.doctorName.text = "$firstName $lastName"
                itemView.doctorStatement.text = statement

                if (uri.isNullOrEmpty()) itemView.doctorAvatar.setImageResource(R.drawable.img_placeholder)
                else itemView.doctorAvatar.load(uri) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                    error(R.drawable.img_placeholder)
                    transformations(CircleCropTransformation())
                }

            }
        }
    }

}
