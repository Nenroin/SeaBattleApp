package com.nenroin.seabattleapp.network

import android.net.wifi.p2p.WifiP2pDevice

interface PeerListCallback {
    fun onPeerListUpdated(deviceList: Collection<WifiP2pDevice>)
}