package com.nenroin.seabattleapp.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nenroin.seabattleapp.databinding.PeerItemBinding
import android.net.wifi.p2p.WifiP2pDevice

class WifiP2pDeviceAdapter : RecyclerView.Adapter<WifiP2pDeviceAdapter.WifiP2pDeviceViewHolder>() {
    private val devices = mutableListOf<WifiP2pDevice>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateDevices(newDevices: List<WifiP2pDevice>) {
        devices.clear()
        devices.addAll(newDevices)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiP2pDeviceViewHolder {
        val binding = PeerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WifiP2pDeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WifiP2pDeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.bind(device)
    }

    override fun getItemCount(): Int = devices.size

    class WifiP2pDeviceViewHolder(private val binding: PeerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(device: WifiP2pDevice) {
            binding.textViewDeviceName.text = device.deviceName
            binding.textViewAddress.text = device.deviceAddress
        }
    }
}
