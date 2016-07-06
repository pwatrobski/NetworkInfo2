package com.example.ptw.networkinfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "NetworkStatusExample";
    private static final String EXC_TAG = "ExceptionFound";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Try Updating Network Connectivity Info Instead...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button button = (Button) findViewById(R.id.button_Update);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //perform action on click

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                boolean isWifiConn = networkInfo.isConnected();
                networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileConn = networkInfo.isConnected();
                Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
                Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

                final TextView wifi_conn = (TextView) findViewById(R.id.wifi_conn);
                wifi_conn.setText("Wifi connected: " + isWifiConn);
                final TextView mob_conn = (TextView) findViewById(R.id.mobile_conn);
                mob_conn.setText("Mobile connected: " + isMobileConn);




                /** min API 1 **********************************************************************
                 * NetworkInfo getActiveNetworkInfo ()
                 * Returns details about the correctly active default data network
                 * Always check isConnected() before initiating network traffic
                 * may return null when there is no default network
                 */
/*
NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
Log.d(DEBUG_TAG, "Active Network: " + activeNetwork);
*/
                Log.d(DEBUG_TAG, "Build.Version.SDK_INT: " + Build.VERSION.SDK_INT);
                Log.d(DEBUG_TAG, "Build.VERSION_CODES.LOLLIPOP: " + Build.VERSION_CODES.LOLLIPOP);
//                Log.d(DEBUG_TAG, "Detailed State: " + activeNetwork.getDetailedState());
/*
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(BLOCKED));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(CAPTIVE_PORTAL_CHECK));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
                Log.d(DEBUG_TAG, "Authenticating" + NetworkInfo.DetailedState(AUTHENTICATING));
*/
                //Log.d(DEBUG_TAG, "Authenticating" + activeNetwork.describeContents());

                String allNetExtraInfo;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                    /** min API 1 - DEPRECATED in API 23 ***********************************************
                     * NetworkInfo[] getAllNetworkInfo ()
                     * ## This method does not support multiple connected networks of the same type
                     * instead use: getAllNetworks() and getNetworkInfo(android.net.Network)
                     */
                    NetworkInfo allNetworkInfo[] = connMgr.getAllNetworkInfo();
                    int netInfo_length = allNetworkInfo.length;
                    Log.d(DEBUG_TAG, "Number of Networks Currently Tracked: " + netInfo_length);

                    allNetExtraInfo = "Extra Info for " + netInfo_length + ":\n";
                    int num = 0;
                    for (int i = 0; i < netInfo_length; i++) {

                        //Dump of all NetworkInfo accessible
                        int contents = allNetworkInfo[i].describeContents();
                        NetworkInfo.DetailedState detailedState = allNetworkInfo[i].getDetailedState();
                        String extraInfo = allNetworkInfo[i].getExtraInfo(); /*********************/
                        String reason = allNetworkInfo[i].getReason();
                        NetworkInfo.State state = allNetworkInfo[i].getState();
                        int subtype = allNetworkInfo[i].getSubtype();
                        String subtypeName = allNetworkInfo[i].getSubtypeName();
                        int type = allNetworkInfo[i].getType();
                        String typeName = allNetworkInfo[i].getTypeName();
                        boolean available = allNetworkInfo[i].isAvailable();
                        boolean connected = allNetworkInfo[i].isConnected();
                        boolean connecting = allNetworkInfo[i].isConnectedOrConnecting();
                        boolean failover = allNetworkInfo[i].isFailover();
                        boolean roaming = allNetworkInfo[i].isRoaming();

                        Log.d(DEBUG_TAG, "[" + i + "] Contents: " + contents);
                        Log.d(DEBUG_TAG, "[" + i + "] Detailed State: " + detailedState);
                        Log.d(DEBUG_TAG, "[" + i + "] Extra Info: " + extraInfo);
                        Log.d(DEBUG_TAG, "[" + i + "] Reason: " + reason);
                        Log.d(DEBUG_TAG, "[" + i + "] State: " + state);
                        Log.d(DEBUG_TAG, "[" + i + "] Subtype: " + subtype);
                        Log.d(DEBUG_TAG, "[" + i + "] Subtype Name: " + subtypeName);
                        Log.d(DEBUG_TAG, "[" + i + "] Type: " + type);
                        Log.d(DEBUG_TAG, "[" + i + "] Type Name: " + typeName);
                        Log.d(DEBUG_TAG, "[" + i + "] Available: " + available);
                        Log.d(DEBUG_TAG, "[" + i + "] Connected: " + connected);
                        Log.d(DEBUG_TAG, "[" + i + "] Connecting: " + connecting);
                        Log.d(DEBUG_TAG, "[" + i + "] Failover: " + failover);
                        Log.d(DEBUG_TAG, "[" + i + "] Roaming: " + roaming);

                        if (extraInfo != null) {
                            allNetExtraInfo = allNetExtraInfo.concat("\t[" + i + "]\t" + extraInfo + "\n");
                            num++;
                        }
                        Log.d(DEBUG_TAG, "Network " + i + ":\t" + extraInfo);
                    }
                    if (num != netInfo_length)
                        allNetExtraInfo = allNetExtraInfo.concat("\t" + (netInfo_length - num) + " null networks\n");

                    /** min API 1 - DEPRECATED in API 21
                     * boolean requestRouteToHost (int networkType, int hostaddress)
                     * instead use: requestNetwork(NetworkRequest, NetworkCallback), bindProcessToNetwork(Network)
                     *   and getSocketFactory() APIs
                     */

                    /** min API 1 - DEPRECATED in API 21
                     * void setNetworkPreference (int preference)
                     * read documentation
                     */

                    /** min API 1 - DEPRECATED in API 21
                     * int startUsingNetworkFeature (int networkType, String feature)
                     */

                    /** min API 1 - DEPRECATED in API 21
                     * int stopUsingNetworkFeature (int networkType, String feature)
                     */



                } else {

                    TelephonyManager teleMgr = (TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
                    String netOp = teleMgr.getNetworkOperatorName();
                    String simOp = teleMgr.getSimOperatorName();
                    Log.d(DEBUG_TAG, "Network Operator Name: " + netOp);
                    Log.d(DEBUG_TAG, "Sim Operator Name: " + simOp);

                    try {
                        CellLocation cellLoc = teleMgr.getCellLocation();
                        Log.d(DEBUG_TAG, "Cell Location: " + cellLoc);

                        List<CellInfo> cellInfo = teleMgr.getAllCellInfo();
                        for (int k = 0; k < cellInfo.size(); k++) {
                            CellInfo info = cellInfo.get(k);
                            Log.d(DEBUG_TAG, "cellInfo[" + k + "]: " + info);
                        }
                        //CellInfoLte cellInfoLte = (CellInfoLte) teleMgr.getCel
                        //CellSignalStrength cellSignalStrength = ((CellSignalStrength) cellInfo).getCel

                            //CellInfoLte lteMgr = (CellInfoLte)getApplicationContext().getSystemService();

                    } catch (Exception e) {
                        Log.d(DEBUG_TAG, "Unable to obtain cell signal information");
                    }

                    /** min API 21 *********************************************************************
                     * Network[] getAllNetworks ()
                     * returns an array of all Networks currently tracked by the framework
                     */
                    Network allNetworks[] = connMgr.getAllNetworks();
                    int numNetworks = allNetworks.length;
                    Log.d(DEBUG_TAG, "Number of Networks Currently Tracked: " + numNetworks);

                    allNetExtraInfo = "Extra Info for " + numNetworks + ":\n";
                    int num_null = 0;
                    for (int i = 0; i < numNetworks; i++) {
                        NetworkInfo allNetworkInfo = connMgr.getNetworkInfo(allNetworks[i]);

                        //Dump of all NetworkInfo accessible
                        int contents = allNetworkInfo.describeContents();
                        NetworkInfo.DetailedState detailedState = allNetworkInfo.getDetailedState();
                        String extraInfo = allNetworkInfo.getExtraInfo(); /*********************/
                        String reason = allNetworkInfo.getReason();
                        NetworkInfo.State state = allNetworkInfo.getState();
                        int subtype = allNetworkInfo.getSubtype();
                        String subtypeName = allNetworkInfo.getSubtypeName();
                        int type = allNetworkInfo.getType();
                        String typeName = allNetworkInfo.getTypeName();
                        boolean available = allNetworkInfo.isAvailable();
                        boolean connected = allNetworkInfo.isConnected();
                        boolean connecting = allNetworkInfo.isConnectedOrConnecting();
                        boolean failover = allNetworkInfo.isFailover();
                        boolean roaming = allNetworkInfo.isRoaming();


//                        String extraInfo = allNetworkInfo.getExtraInfo();
                        if (extraInfo != null) {
                            allNetExtraInfo = allNetExtraInfo.concat("\t[" + i + "]\t" + extraInfo + "\n");

                            allNetExtraInfo = allNetExtraInfo.concat("\tContents: " + contents + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tDetailed State: " + detailedState + "\n");
                            //allNetExtraInfo = allNetExtraInfo.concat("\tExtra Info" + extraInfo + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tReason: " + reason + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tState: " + state + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tSubtype: " + subtype + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tSubtype Name: " + subtypeName + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tType: " + type + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tType Name: " + typeName + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tAvailable: " + available + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tConnected: " + connected + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tConnecting: " + connecting + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tFailover: " + failover + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tRoaming: " + roaming + "\n");
                        } else {
                            num_null++;
                        }
                        Log.d(DEBUG_TAG, "Network " + i + ":\t" + extraInfo);


                        Log.d(DEBUG_TAG, "[" + i + "] Contents: " + contents);
                        Log.d(DEBUG_TAG, "[" + i + "] Detailed State: " + detailedState);
                        Log.d(DEBUG_TAG, "[" + i + "] Extra Info: " + extraInfo);
                        Log.d(DEBUG_TAG, "[" + i + "] Reason: " + reason);
                        Log.d(DEBUG_TAG, "[" + i + "] State: " + state);
                        Log.d(DEBUG_TAG, "[" + i + "] Subtype: " + subtype);
                        Log.d(DEBUG_TAG, "[" + i + "] Subtype Name: " + subtypeName);
                        Log.d(DEBUG_TAG, "[" + i + "] Type: " + type);
                        Log.d(DEBUG_TAG, "[" + i + "] Type Name: " + typeName);
                        Log.d(DEBUG_TAG, "[" + i + "] Available: " + available);
                        Log.d(DEBUG_TAG, "[" + i + "] Connected: " + connected);
                        Log.d(DEBUG_TAG, "[" + i + "] Connecting: " + connecting);
                        Log.d(DEBUG_TAG, "[" + i + "] Failover: " + failover);
                        Log.d(DEBUG_TAG, "[" + i + "] Roaming: " + roaming);

                        /** min API 21 *********************************************************************
                         * NetworkCapabilities getNetworkCapabilities (Network network)
                         */
                        NetworkCapabilities networkCapabilities = connMgr.getNetworkCapabilities(allNetworks[i]);
                        int netCap_content = networkCapabilities.describeContents();
                        int dnBW = networkCapabilities.getLinkDownstreamBandwidthKbps();
                        int upBW = networkCapabilities.getLinkUpstreamBandwidthKbps();
                        boolean trusted = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED);
                        boolean validated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                        boolean internet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                        boolean cellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                        boolean wifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                        boolean vpn = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);

                        if (extraInfo != null) {
                            allNetExtraInfo = allNetExtraInfo.concat("\tNetwork Capabilities Content: " + netCap_content + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tDownlink BW: " + dnBW + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tUplink BW: " + upBW + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tTrusted: " + trusted + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tValidated: " + validated + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tInternet: " + internet + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tCellular: " + cellular + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tWiFi: " + wifi + "\n");
                            allNetExtraInfo = allNetExtraInfo.concat("\tVPN: " + vpn + "\n");
                        }

                        Log.d(DEBUG_TAG, "[" + i + "] Network Capabilities Content: " + netCap_content);
                        Log.d(DEBUG_TAG, "[" + i + "] Downlink BW: " + dnBW);
                        Log.d(DEBUG_TAG, "[" + i + "] Uplink BW: " + upBW);
                        Log.d(DEBUG_TAG, "[" + i + "] Trusted: " + trusted);
                        Log.d(DEBUG_TAG, "[" + i + "] Validated: " + validated);
                        Log.d(DEBUG_TAG, "[" + i + "] Internet: " + internet);
                        Log.d(DEBUG_TAG, "[" + i + "] Cellular: " + cellular);
                        Log.d(DEBUG_TAG, "[" + i + "] WiFi: " + wifi);
                        Log.d(DEBUG_TAG, "[" + i + "] VPN: " + vpn);


                        /** min API 21
                         * LinkProperties getLinkProperties (Network network)
                         * returns the LinkProperties for the given Network, (returns null if network is unknown)
                         */
                        LinkProperties linkProperties = connMgr.getLinkProperties(allNetworks[i]);
                        List<InetAddress> inetAddressList = linkProperties.getDnsServers();
                        String domains = linkProperties.getDomains();
                        ProxyInfo httpProxy = linkProperties.getHttpProxy();
                        String interfaceName = linkProperties.getInterfaceName();
                        List<LinkAddress> linkAddressList = linkProperties.getLinkAddresses();
                        List<RouteInfo> routeInfoList = linkProperties.getRoutes();
                        int hash = linkProperties.hashCode();

                        for (int j = 0; j < inetAddressList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] DNS Server[" + j + "]: " + inetAddressList.get(j));
                            allNetExtraInfo = allNetExtraInfo.concat("\tDNS Server[" + j + "]: " + inetAddressList.get(j) + "\n");
                        }
                        Log.d(DEBUG_TAG, "[" + i + "] Domains: " + domains);
                        allNetExtraInfo = allNetExtraInfo.concat("\tDomains: " + domains + "\n");
                        Log.d(DEBUG_TAG, "[" + i + "] http Proxy: " + httpProxy);
                        allNetExtraInfo = allNetExtraInfo.concat("\tHTTP Proxy: " + httpProxy + "\n");
                        Log.d(DEBUG_TAG, "[" + i + "] Interface Name: " + interfaceName);
                        allNetExtraInfo = allNetExtraInfo.concat("\tInterface Name: " + interfaceName + "\n");
                        for (int j = 0; j < linkAddressList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] Link Address[" + j + ": " + linkAddressList.get(j));
                            allNetExtraInfo = allNetExtraInfo.concat("\tLink Address[" + j + "]: " + linkAddressList.get(j) + "\n");
                        }

                        for (int j = 0; j < routeInfoList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] Route Info: " + routeInfoList.get(j));
                            allNetExtraInfo = allNetExtraInfo.concat("\tRoute Info[" + j + "]: " + routeInfoList.get(j) + "\n");
                        }
                        Log.d(DEBUG_TAG, "[" + i + "] Hash: " + Integer.toHexString(hash));
                        allNetExtraInfo = allNetExtraInfo.concat("\tHash: " + Integer.toHexString(hash) + "\n");

                    }
                    if (num_null != 0)
                        allNetExtraInfo = allNetExtraInfo.concat("\t" + num_null + " null networks\n");


                    /** min API 23 *********************************************************************
                     * boolean bindProcessToNetwork (Network network)
                     * binds the current process to network. all sockets created in the future (not
                     *   explicitly bound via SocketFactory - Network.getSocketFactory() ) will be bound
                     *   to network
                     * if network ever disconnects, all sockets created in this way will cease to work
                     *   and all host name resolutions will fail
                     * Parameters:
                     *  network  Network: the network to bind the current process to, or null to clear
                     *    the current binding
                     */

                    /** min API 23 *********************************************************************
                     * Network getActiveNetwork()
                     * returns object corresponding to the current active default data network
                     */

                    /** min API 24
                     * void registerDefaultNetworkCallback (ConnectivityManager.NetworkCallback networkCallback)
                     * registers to receive notification about changes in the system default network
                     * to cancel: unregisterNetworkCallback(NetworkCallback)
                     */

                    /** min API 21
                     * void registerNetworkCallback (NetworkRequest request, ConnectivityManager.NetworkCallback networkCallback)
                     * registers to receive notifications about all networks which satisfy the given NetworkRequest
                     * to cancel: unregisterNetworkCallback(NetworkCallback)
                     */

                    /** min API 23
                     * void registerNetworkCallback (NetworkRequest request, PendingIntent operation)
                     * does stuff... read the documentation
                     */

                    /** min API 22
                     * void releaseNetworkRequest (PendingIntent Operation)
                     * opposite of previous? read the documentation
                     */

                    /** min API 21
                     * void removeDefaultNetworkActiveListener (ConnectivityManager.OnNetworkActiveListener 1)
                     */

                    /** min API 21 - DEPRECATED in API 23
                     * void reportBadNetwork (Network network)
                     * instead use: reportNetworkConnectivity(Network, boolean) which allows reporting
                     *   for both working and non-working connectivity
                     */

                    /**********************************************************************************/
                    /** min API 23 *********************************************************************
                     * void reportNetworkConnectivity(Network network, boolean hasConnectivity)
                     */

                    /** min API 23
                     * boolean requestBandwidthUpdate (Network network)
                     * returns whether the update request is accepted by ConnectivityService.
                     * The caller will be notified via ConnectivityManager.NetworkCallback if there is
                     *   an update
                     * NOTICE: assumes that the caller has previously called registerNetworkCallback(NetworkRequest, NetworkCallback)
                     */

                    /**********************************************************************************/
                    /** min API 21 *********************************************************************
                     * void requestNetwork (NetworkRequest request, ConnectivityManager.NetworkCallback networkCallback)
                     * request a network to satisfy a set of NetworkCapabilities
                     *   will live until unregisterNetworkCallback(NetworkCallback)
                     * unsupported to request a network with mutable NetworkCapabilities such as NET_CAPABILITY_VALIDATED
                     *   or NET_CAPABILITY_CAPTIVE_PORTAL
                     */

                    /**********************************************************************************/
                    /** min API 22
                     * void requestNetwork (NetworkRequest request, PendingIntent operation)
                     * behaves identically to the version that takes a NetworkCallback, but uses a PendingIntent instead
                     */


                    /** min API 21
                     * void unregisterNetworkCallback (ConnectivityManger.NetworkCallback networkCallback)
                     */

                    /** min API 23
                     * void unregisterNetworkCallback (PendingIntent operation)
                     */
                }

                //Print out the network information
                final TextView all_net = (TextView) findViewById(R.id.new_net);
                all_net.setText(allNetExtraInfo);
                all_net.setMovementMethod(new ScrollingMovementMethod());


            }
        });

        final Button buttonCap = (Button) findViewById(R.id.button_Capability);
        buttonCap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                String allNetworkCapabilities;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    allNetworkCapabilities = "Version too early...\n";
                } else {
                    Network allNetworks[] = connMgr.getAllNetworks();
                    int numNetworks = allNetworks.length;
                    Log.d(DEBUG_TAG, "Number of Networks Currently Tracked: " + numNetworks);

                    allNetworkCapabilities = "Extra Info for " + numNetworks + ":\n";
                    for (int i = 0; i < numNetworks; i++) {
                        /** min API 21 *********************************************************************
                         * NetworkCapabilities getNetworkCapabilities (Network network)
                         */
                        NetworkCapabilities networkCapabilities = connMgr.getNetworkCapabilities(allNetworks[i]);
                        int netCap_content = networkCapabilities.describeContents();
                        int dnBW = networkCapabilities.getLinkDownstreamBandwidthKbps();
                        int upBW = networkCapabilities.getLinkUpstreamBandwidthKbps();
                        boolean trusted = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED);
                        boolean validated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                        boolean internet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                        boolean cellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                        boolean wifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                        boolean vpn = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);

                        if (networkCapabilities != null) {
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tNetwork Capabilities Content: " + netCap_content + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tDownlink BW: " + dnBW + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tUplink BW: " + upBW + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tTrusted: " + trusted + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tValidated: " + validated + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tInternet: " + internet + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tCellular: " + cellular + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tWiFi: " + wifi + "\n");
                            allNetworkCapabilities = allNetworkCapabilities.concat("\tVPN: " + vpn + "\n");
                        }

                        Log.d(DEBUG_TAG, "[" + i + "] Network Capabilities Content: " + netCap_content);
                        Log.d(DEBUG_TAG, "[" + i + "] Downlink BW: " + dnBW);
                        Log.d(DEBUG_TAG, "[" + i + "] Uplink BW: " + upBW);
                        Log.d(DEBUG_TAG, "[" + i + "] Trusted: " + trusted);
                        Log.d(DEBUG_TAG, "[" + i + "] Validated: " + validated);
                        Log.d(DEBUG_TAG, "[" + i + "] Internet: " + internet);
                        Log.d(DEBUG_TAG, "[" + i + "] Cellular: " + cellular);
                        Log.d(DEBUG_TAG, "[" + i + "] WiFi: " + wifi);
                        Log.d(DEBUG_TAG, "[" + i + "] VPN: " + vpn);
                    }
                }
                //Print out the network information
                final TextView netCap = (TextView) findViewById(R.id.netCap);
                netCap.setText(allNetworkCapabilities);
                netCap.setMovementMethod(new ScrollingMovementMethod());
            }
        });

        final Button buttonProp = (Button) findViewById(R.id.button_Properties);
        buttonProp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                String allNetworkLinkInfo;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    allNetworkLinkInfo = "API Version too early...";
                } else {
                    Network allNetworks[] = connMgr.getAllNetworks();
                    int numNetworks = allNetworks.length;
                    Log.d(DEBUG_TAG, "Number of Networks Currently Tracked: " + numNetworks);

                    allNetworkLinkInfo = "Extra Info for " + numNetworks + ":\n";

                    int num_null = 0;
                    for (int i = 0; i < numNetworks; i++) {
                        /** min API 21
                         * LinkProperties getLinkProperties (Network network)
                         * returns the LinkProperties for the given Network, (returns null if network is unknown)
                         */

                        LinkProperties linkProperties = connMgr.getLinkProperties(allNetworks[i]);
                        List<InetAddress> inetAddressList = linkProperties.getDnsServers();
                        String domains = linkProperties.getDomains();
                        ProxyInfo httpProxy = linkProperties.getHttpProxy();
                        String interfaceName = linkProperties.getInterfaceName();
                        List<LinkAddress> linkAddressList = linkProperties.getLinkAddresses();
                        List<RouteInfo> routeInfoList = linkProperties.getRoutes();
                        int hash = linkProperties.hashCode();

                        for (int j = 0; j < inetAddressList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] DNS Server[" + j + "]: " + inetAddressList.get(j));
                            allNetworkLinkInfo = allNetworkLinkInfo.concat("\tDNS Server[" + j + "]: " + inetAddressList.get(j) + "\n");
                        }
                        Log.d(DEBUG_TAG, "[" + i + "] Domains: " + domains);
                        allNetworkLinkInfo = allNetworkLinkInfo.concat("\tDomains: " + domains + "\n");
                        Log.d(DEBUG_TAG, "[" + i + "] http Proxy: " + httpProxy);
                        allNetworkLinkInfo = allNetworkLinkInfo.concat("\tHTTP Proxy: " + httpProxy + "\n");
                        Log.d(DEBUG_TAG, "[" + i + "] Interface Name: " + interfaceName);
                        allNetworkLinkInfo = allNetworkLinkInfo.concat("\tInterface Name: " + interfaceName + "\n");
                        for (int j = 0; j < linkAddressList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] Link Address[" + j + ": " + linkAddressList.get(j));
                            allNetworkLinkInfo = allNetworkLinkInfo.concat("\tLink Address[" + j + "]: " + linkAddressList.get(j) + "\n");
                        }

                        for (int j = 0; j < routeInfoList.size(); j++) {
                            Log.d(DEBUG_TAG, "[" + i + "] Route Info: " + routeInfoList.get(j));
                            allNetworkLinkInfo = allNetworkLinkInfo.concat("\tRoute Info[" + j + "]: " + routeInfoList.get(j) + "\n");
                        }
                        Log.d(DEBUG_TAG, "[" + i + "] Hash: " + Integer.toHexString(hash));
                        allNetworkLinkInfo = allNetworkLinkInfo.concat("\tHash: " + Integer.toHexString(hash) + "\n");

                    }
                    if (num_null != 0)
                        allNetworkLinkInfo = allNetworkLinkInfo.concat("\t" + num_null + " null networks\n");
                }

               //Print out the network information
               final TextView netProp = (TextView) findViewById(R.id.netProp);
                netProp.setText(allNetworkLinkInfo);
                netProp.setMovementMethod(new ScrollingMovementMethod());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        //Do something in response to button
/**        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
*/
    }

}
