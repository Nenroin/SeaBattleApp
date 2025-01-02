package com.nenroin.seabattleapp.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.nenroin.seabattleapp.databinding.ActivityConnectMenuBinding
import com.nenroin.seabattleapp.network.PeerListCallback
import com.nenroin.seabattleapp.network.WiFiDirectBroadcastReceiver
import com.nenroin.seabattleapp.utils.WifiP2pDeviceAdapter

class ConnectMenuActivity : AppCompatActivity(), PeerListCallback {
    private lateinit var binding: ActivityConnectMenuBinding

    private lateinit var manager: WifiP2pManager

    private var channel: WifiP2pManager.Channel? = null
    private var receiver: BroadcastReceiver? = null

    private val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConnectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager

        channel = manager.initialize(this, mainLooper) {
            Toast.makeText(
                this@ConnectMenuActivity,
                "Channel disconnected! Reinitializing...",
                Toast.LENGTH_SHORT
            ).show()
            channel = manager.initialize(this@ConnectMenuActivity, mainLooper, null)
        }

        channel?.also { channel ->
            receiver = WiFiDirectBroadcastReceiver(manager, channel, this)
        }

        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, "Wi-Fi is disabled. Please enable it.", Toast.LENGTH_SHORT).show()
            return
        }

        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@ConnectMenuActivity,
                    "Searching for peers...",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(reasonCode: Int) {
                when (reasonCode) {
                    WifiP2pManager.P2P_UNSUPPORTED -> {
                        Toast.makeText(
                            this@ConnectMenuActivity,
                            "Wi-Fi Direct is not supported on this device.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    WifiP2pManager.BUSY -> {
                        Toast.makeText(
                            this@ConnectMenuActivity,
                            "Wi-Fi P2P is busy. Please try again later.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    WifiP2pManager.ERROR -> {
                        Toast.makeText(
                            this@ConnectMenuActivity,
                            "An error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(
                            this@ConnectMenuActivity,
                            "Failed to start peer discovery: $reasonCode",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        receiver?.also { receiver ->
            registerReceiver(receiver, intentFilter, RECEIVER_NOT_EXPORTED)
        }
    }

    override fun onPause() {
        super.onPause()
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }

    override fun onPeerListUpdated(deviceList: Collection<WifiP2pDevice>) {
        val peerList = binding.recyclerViewPeerList
        val adapter = WifiP2pDeviceAdapter()

        if (deviceList.isEmpty()) {
            Toast.makeText(this, "No peers found", Toast.LENGTH_SHORT).show()
        } else {
            deviceList.forEach { device ->
                Log.d("PeerList", "Device: ${device.deviceName}, Address: ${device.deviceAddress}")
            }
        }

        peerList.adapter = adapter

        adapter.updateDevices(deviceList.toList())
    }
}
