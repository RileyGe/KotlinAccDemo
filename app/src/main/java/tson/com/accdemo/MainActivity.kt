package tson.com.accdemo

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.junkchen.blelib.BleListener
import com.junkchen.blelib.MultipleBleService
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBleService : MultipleBleService  //可以为null的变量

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBleService = (service as MultipleBleService.LocalBinder).service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            //do nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceIntent = Intent(this, MultipleBleService::class.java)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun setBleServiceListener() {


        //问题就出在这里，如果是BleListener.OnDataAvailableListener的话无法访问
        //如果是MultipleBleService.OnDataAvailableListener的话则类型不对
        mBleService.setOnDataAvailableListener(object : BleListener.OnDataAvailableListener{
            override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                //do something
            }

            override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {}

            override fun onDescriptorRead(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {}
        })
    }
}
